<template>
  <div class="history-table">
    <el-table :data="historyList" style="width: 100%" v-loading="loading">
      <el-table-column prop="title" label="对话标题" min-width="150">
        <template #default="{ row }">
          <div class="title-cell">
            <el-icon><ChatLineRound /></el-icon>
            <span>{{ row.title || '新对话' }}</span>
          </div>
        </template>
      </el-table-column>
      
      <el-table-column prop="message" label="用户消息" min-width="200">
        <template #default="{ row }">
          <div class="message-cell">
            {{ row.message }}
          </div>
        </template>
      </el-table-column>
      
      <el-table-column prop="answer" label="AI回答" min-width="200">
        <template #default="{ row }">
          <div class="answer-cell">
            {{ row.answer }}
          </div>
        </template>
      </el-table-column>
      
      <el-table-column prop="createTime" label="创建时间" width="180">
        <template #default="{ row }">
          {{ formatDate(row.createTime) }}
        </template>
      </el-table-column>
      
      <el-table-column label="操作" width="100" fixed="right">
        <template #default="{ row }">
          <div class="operation-cell">
            <el-button type="danger" size="small" @click="deleteChat(row.id)">
              删除
            </el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>
    
    <div class="empty-placeholder" v-if="historyList.length === 0 && !loading">
      <el-empty description="暂无历史对话记录" />
    </div>
    
    <div class="pagination" v-if="historyList.length > 0">
      <el-pagination
        background
        layout="prev, pager, next"
        :total="total"
        :page-size="pageSize"
        :current-page="currentPage"
        @current-change="handlePageChange"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { ChatLineRound } from '@element-plus/icons-vue';
import axios from 'axios';

interface ChatHistory {
  id: string | number;
  title?: string;
  message: string;
  answer: string;
  createTime?: number;
  updateTime?: number;
}

const historyList = ref<ChatHistory[]>([]);
const loading = ref(false);
const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);

// 格式化日期
const formatDate = (timestamp: number) => {
  const date = new Date(timestamp);
  return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')} ${String(date.getHours()).padStart(2, '0')}:${String(date.getMinutes()).padStart(2, '0')}`;
};

// 加载历史记录
const loadHistoryList = async () => {
  loading.value = true;
  try {
    // 调用实际的API
    const response = await axios.post(`http://127.0.0.1:8080/auth/data`);
    
    // 根据后端返回的数据格式进行处理
    if (response.data && response.data.status === 'success') {
      // tell字段包含历史记录数据
      const data = response.data.tell;
      
      // 处理数据
      if (Array.isArray(data)) {
        // 直接使用数组数据
        historyList.value = data.map((item, index) => ({
          id: item.id || `chat-${index}`,
          title: generateTitle(item.message) || `对话 ${index + 1}`,
          message: item.message,
          answer: item.answer,
          createTime: item.createTime || Date.now() - index * 86400000
        }));
        total.value = data.length;
      } else if (typeof data === 'string') {
        try {
          // 尝试解析JSON字符串
          const parsedData = JSON.parse(data);
          if (Array.isArray(parsedData)) {
            historyList.value = parsedData.map((item, index) => ({
              id: item.id || `chat-${index}`,
              title: generateTitle(item.message) || `对话 ${index + 1}`,
              message: item.message,
              answer: item.answer,
              createTime: item.createTime || Date.now() - index * 86400000
            }));
            total.value = parsedData.length;
          } else {
            ElMessage.warning('历史记录数据格式不正确');
            useSimulatedData();
          }
        } catch (e) {
          ElMessage.warning('解析历史记录数据失败');
          useSimulatedData();
        }
      } else {
        ElMessage.warning('未找到有效的历史记录数据');
        useSimulatedData();
      }
    } else {
      // 如果返回状态不是success，使用模拟数据
      useSimulatedData();
    }
    
    loading.value = false;
  } catch (error) {
    ElMessage.error('加载历史记录失败');
    loading.value = false;
    // 出错时使用模拟数据
    useSimulatedData();
  }
};

// 根据消息内容生成标题
const generateTitle = (message: string): string => {
  if (!message) return '新对话';
  // 截取消息的前15个字符作为标题
  return message.length > 15 ? `${message.substring(0, 15)}...` : message;
};

// 使用模拟数据的函数
const useSimulatedData = () => {
  historyList.value = Array.from({ length: 5 }, (_, i) => ({
    id: `chat-${i}`,
    title: `对话 ${i + 1}`,
    message: `模拟消息 ${i + 1}`,
    answer: `模拟回答 ${i + 1}`,
    createTime: Date.now() - i * 86400000,
    updateTime: Date.now() - i * 86400000
  }));
  total.value = 15;
};

// 页码变化
const handlePageChange = (page: number) => {
  currentPage.value = page;
  loadHistoryList();
};

// 删除对话
const deleteChat = (id: string) => {
  ElMessageBox.confirm('确定要删除这个对话吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      // 调用实际的API
      await axios.delete(`http://127.0.0.1:8080/auth/data?id=${id}`);
      
      // 模拟删除
      historyList.value = historyList.value.filter(item => item.id !== id);
      ElMessage.success('删除成功');
      
      // 如果当前页没有数据了，且不是第一页，则回到上一页
      if (historyList.value.length === 0 && currentPage.value > 1) {
        currentPage.value--;
        loadHistoryList();
      }
    } catch (error) {
      ElMessage.error('删除失败');
    }
  }).catch(() => {});
};

onMounted(() => {
  loadHistoryList();
});
</script>

<style lang="scss" scoped>
.history-table {
  width: 100%;
  
  :deep(.el-table) {
    border-radius: 8px;
    overflow: hidden;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
    
    .el-table__row {
      transition: all 0.3s ease;
      
      &:hover {
        background-color: #f5f7fa;
      }
    }
  }
  
  .title-cell {
    display: flex;
    align-items: center;
    gap: 8px;
    font-weight: 500;
    
    .el-icon {
      color: #409eff;
      font-size: 18px;
    }
  }
  
  .message-cell, .answer-cell {
    max-height: 100px;
    overflow-y: auto;
    line-height: 1.5;
    padding: 4px 0;
    white-space: pre-wrap;
    word-break: break-word;
  }
  
  .operation-cell {
    display: flex;
    justify-content: center;
    
    .el-button {
      padding: 6px 12px;
      border-radius: 4px;
      transition: all 0.3s;
      
      &:hover {
        transform: translateY(-2px);
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
      }
    }
  }
  
  .empty-placeholder {
    padding: 60px 0;
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
  }
  
  .pagination {
    margin-top: 20px;
    text-align: center;
    
    :deep(.el-pagination) {
      justify-content: center;
    }
  }
}
</style>