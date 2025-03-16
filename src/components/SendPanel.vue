<template>
  <div class="send-panel">
    <div class="input-container">
      <el-input
        v-model="messageText"
        type="textarea"
        :rows="1"
        :autosize="{ minRows: 1, maxRows: 5 }"
        placeholder="输入消息..."
        @keydown.enter.exact.prevent="handleSend"
        :disabled="loading"
      />
      <div class="send-button">
        <el-button
          type="primary"
          :icon="loading ? 'Loading' : 'Position'"
          circle
          @click="handleSend"
          :loading="loading"
          :disabled="!messageText.trim() || loading"
        />
      </div>
    </div>
    <div class="tips">
      <span>按 Enter 发送，Shift + Enter 换行</span>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { Position, Loading } from '@element-plus/icons-vue';

const props = defineProps<{
  loading: boolean;
}>();

const emit = defineEmits<{
  send: [content: string];
}>();

const messageText = ref('');

const handleSend = () => {
  if (!messageText.value.trim() || props.loading) return;
  
  emit('send', messageText.value);
  messageText.value = '';
};
</script>

<style lang="scss" scoped>
.send-panel {
  padding: 16px;
  border-top: 1px solid #eaeaea;
  background: #fff;
  width: 100%;
  max-width: min(900px, 95%);
  margin: 0 auto;
  
  .input-container {
    position: relative;
    display: flex;
    align-items: flex-end;
    
    .el-input {
      flex: 1;
      
      :deep(.el-textarea__inner) {
        padding-right: 50px;
        resize: none;
        border-radius: 12px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
        transition: all 0.3s ease;
        font-size: 16px;
        line-height: 1.6;
        
        &:focus {
          box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }
      }
    }
    
    .send-button {
      position: absolute;
      right: 8px;
      bottom: 8px;
      
      .el-button {
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        transition: all 0.3s ease;
        
        &:hover:not(:disabled) {
          transform: translateY(-2px);
          box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        }
      }
    }
  }
  
  .tips {
    margin-top: 8px;
    text-align: center;
    font-size: 12px;
    color: #999;
  }
}

@media (max-width: 768px) {
  .send-panel {
    padding: 12px;
    
    .tips {
      display: none;
    }
  }
}
</style>