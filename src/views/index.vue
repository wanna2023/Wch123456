<script setup lang="ts">
import { ref, onMounted, nextTick } from 'vue';
import MessageList from '@/components/MessageList.vue';
import SendPanel from '@/components/SendPanel.vue';
import HistoryTable from '@/components/HistoryTable.vue';
import axios from 'axios';
import { ElMessage } from 'element-plus';
import { Delete, Document, Plus, SwitchButton } from '@element-plus/icons-vue';
import { useRouter } from 'vue-router';

interface Message {
  type: 0 | 1; // 0: 用户消息, 1: AI消息
  content: string;
  loading?: boolean;
}

const messages = ref<Message[]>([]);
const loading = ref(false);
const showHistoryDialog = ref(false);
const router = useRouter();

// 初始化欢迎消息
onMounted(() => {
  messages.value.push({
    type: 1,
    content: '你好！我是梗大师，有什么可以帮助你的吗？'
  });
  
  // 确保滚动到底部
  scrollToBottom();
});

// 滚动到底部的函数
const scrollToBottom = async () => {
  await nextTick();
  const messageList = document.querySelector('.message-list');
  if (messageList) {
    messageList.scrollTop = messageList.scrollHeight;
  }
};

const sendMessage = async (content: string) => {
  if (!content.trim()) {
    ElMessage.warning('请输入消息内容');
    return;
  }
  
  // 添加用户消息
  messages.value.push({ type: 0, content });
  
  // 添加AI消息占位（带loading状态）
  messages.value.push({ type: 1, content: '', loading: true });
  
  // 滚动到底部
  scrollToBottom();
  
  loading.value = true;
  
  try {
    // 使用POST请求发送消息
    const response = await axios.post('http://127.0.0.1:8080/auth/ai', {
      message: content
    });
    
    if (response.data && response.data.status === 'success') {
      // 更新最后一条消息
      messages.value[messages.value.length - 1].loading = false;
      messages.value[messages.value.length - 1].content = response.data.answer;
      
      // 自动滚动到底部
      scrollToBottom();
    } else {
      throw new Error(response.data?.message || '请求失败');
    }
    
    loading.value = false;
  } catch (error: any) {
    messages.value.pop(); // 移除loading消息
    ElMessage.error(error.message || '发送消息失败');
    loading.value = false;
  }
};

const showHistory = () => {
  showHistoryDialog.value = true;
};

// 清空当前对话
const clearChat = () => {
  ElMessage.success('对话已清空');
  messages.value = [{
    type: 1,
    content: '你好！我是AI助手，有什么可以帮助你的吗？'
  }];
};

// 新建对话
const newChat = () => {
  clearChat();
  ElMessage.success('已创建新对话');
};

// 退出登录
const logout = () => {
  ElMessage.success('已退出登录');
  // 跳转到登录页
  router.push('/');
};
</script>

<template>
  <div class="chat-container">
    <div class="sidebar">
      <div class="logo">
        <img src="@/assets/deepseek.png" alt="logo" />
        <h2>梗大师</h2>
      </div>
      
      <div class="sidebar-buttons">
        <div class="new-chat" @click="newChat">
          <el-button type="primary" icon="Plus" round>
            新建对话
          </el-button>
        </div>
        
        <el-button @click="showHistory" icon="Document" class="history-button">
          历史记录
        </el-button>
        
        <el-button @click="logout" icon="SwitchButton" type="info">
          退出登录
        </el-button>
        
        <el-button type="danger" @click="clearChat" icon="Delete">
          清空对话
        </el-button>
      </div>
      
      <div class="sidebar-footer">
        <p>© 2023 AI Assistant</p>
      </div>
    </div>
    
    <div class="main-content">
      <MessageList :messages="messages" />
      <SendPanel @send="sendMessage" :loading="loading" />
    </div>
  </div>
  
  <!-- 历史记录对话框 -->
  <el-dialog
    v-model="showHistoryDialog"
    title="历史对话记录"
    width="80%"
    destroy-on-close
  >
    <HistoryTable />
  </el-dialog>
</template>

<style lang="scss" scoped>
.chat-container {
  display: flex;
  height: 100vh;
  background: #f5f7fa;
  overflow: hidden;

  .sidebar {
    width: 260px;
    padding: 20px;
    background: #ffffff;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
    display: flex;
    flex-direction: column;
    border-right: 1px solid #eaeaea;

    .logo {
      display: flex;
      align-items: center;
      margin-bottom: 24px;
      
      img {
        width: 40px;
        height: 40px;
        border-radius: 8px;
      }
      
      h2 {
        margin-left: 12px;
        font-size: 18px;
        font-weight: 600;
        color: #333;
      }
    }
    
    .sidebar-buttons {
      display: flex;
      flex-direction: column;
      gap: 12px;
      margin-bottom: auto;
      
      .new-chat {
        margin-bottom: 8px;
        
        .el-button {
          width: 100%;
        }
      }
      
      .history-button {
        width: 100%;
      }
      
      .el-button {
        justify-content: flex-start;
        padding-left: 16px;
      }
    }
    
    .sidebar-footer {
      margin-top: auto;
      font-size: 12px;
      color: #999;
      text-align: center;
    }
  }

  .main-content {
    flex: 1;
    display: flex;
    flex-direction: column;
    height: 100%;
    overflow: hidden;
    
    .message-list {
      flex: 1;
      overflow-y: auto;
    }
  }
}
</style>