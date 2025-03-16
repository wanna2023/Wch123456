<template>
  <div class="message-list" ref="messageListRef">
    <div v-for="(message, index) in messages" :key="index" class="message-item" :class="{ 'user-message': message.type === 0, 'ai-message': message.type === 1 }">
      <div class="user-icon" v-if="message.type === 1">AI</div>
      <div class="message-content">
        <div class="content-inner">
          <template v-if="message.type === 1">
            <div v-if="message.loading" class="loading">
              <img src="@/assets/loading.gif" alt="loading" />
            </div>
            <template v-else>
              <div class="ai-content">{{ message.content }}</div>
            </template>
          </template>
          <template v-else>
            <div class="user-content">{{ message.content }}</div>
          </template>
        </div>
      </div>
      <div class="user-icon user" v-if="message.type === 0">U</div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch, nextTick } from 'vue';

interface Message {
  type: 0 | 1; // 0: 用户消息, 1: AI消息
  content: string | string[];
  loading?: boolean;
}

const props = defineProps<{
  messages: Message[];
}>();

const messageListRef = ref<HTMLElement | null>(null);

// 监听消息变化，自动滚动到底部
watch(
  () => props.messages,
  () => {
    scrollToBottom();
  },
  { deep: true }
);

onMounted(() => {
  scrollToBottom();
});

// 滚动到底部的方法
const scrollToBottom = async () => {
  await nextTick();
  if (messageListRef.value) {
    messageListRef.value.scrollTop = messageListRef.value.scrollHeight;
  }
};

// 暴露滚动方法给父组件
defineExpose({
  scrollToBottom
});
</script>

<style lang="scss" scoped>
.message-list {
  padding: clamp(15px, 3vw, 30px);
  height: 100%;
  overflow-y: auto;
  width: 100%;
  max-width: min(900px, 95%);
  margin: 0 auto;
  scrollbar-width: thin;
  
  &::-webkit-scrollbar {
    width: 6px;
  }
  
  &::-webkit-scrollbar-thumb {
    background-color: rgba(0, 0, 0, 0.2);
    border-radius: 3px;
  }
  
  &::-webkit-scrollbar-track {
    background-color: transparent;
  }

  .message-item {
    margin: clamp(12px, 1.5vh, 20px) 0;
    display: flex;
    align-items: flex-start;
    gap: clamp(8px, 2vw, 16px);
    animation: fadeIn 0.3s ease;

    &.user-message {
      flex-direction: row-reverse;
      justify-content: flex-end;

      .message-content {
        .content-inner {
          background: var(--primary-color, #409eff);
          color: #fff;
          border-radius: 12px 12px 0 12px;
        }
      }
    }

    &.ai-message {
      justify-content: flex-start;
      
      .message-content {
        .content-inner {
          background: #f4f6f8;
          color: #333;
          border-radius: 12px 12px 12px 0;
        }
      }
    }

    .user-icon {
      flex-shrink: 0;
      width: clamp(36px, 5vw, 40px);
      height: clamp(36px, 5vw, 40px);
      line-height: clamp(36px, 5vw, 40px);
      border-radius: 50%;
      font-size: clamp(14px, 2vw, 16px);
      font-weight: 500;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
      text-align: center;
      
      &.user {
        background: #409eff;
        color: white;
      }
      
      &:not(.user) {
        background: #f4f6f8;
        color: #333;
      }
    }

    .message-content {
      max-width: min(85%, calc(100% - 64px));

      .content-inner {
        padding: 16px 20px;
        border-radius: 12px;
        font-size: 16px;
        line-height: 1.6;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
        white-space: pre-wrap;
        word-break: break-word;
      }
      
      .ai-content, .user-content {
        white-space: pre-wrap;
        word-break: break-word;
      }
    }
  }
}

.loading {
  display: flex;
  justify-content: center;
  margin: 8px 0;

  img {
    width: 20px;
    height: 20px;
  }
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>