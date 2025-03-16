<template>
    <div id="app"></div>
    <Chat
      :onMessageSend="sendMessage"
      :chats="chats"
      :roleConfig="roleInfo"
      ref="chatRef"
      style="margin: 0 auto"
    ></Chat>
  </template>
  <script setup lang="ts">
  import { ref, useTemplateRef } from 'vue';
  import { Chat } from '@kousum/semi-ui-vue';
  import logo from '@/assets/deepseek.png';

  interface ChatMessage {
    role: 'system' | 'user' | 'assistant';
    id: string | number;
    createAt: number;
    content: string;
    status?: 'loading' | 'incomplete' | 'complete' | 'error';
  }

  interface RoleConfig {
    name: string;
    avatar: string;
  }

  interface RoleInfo {
    user: RoleConfig;
    assistant: RoleConfig;
    system: RoleConfig;
  }

  const chatRef = useTemplateRef<any>('chatRef');
  
  const chats = ref<ChatMessage[]>([]);
  const roleInfo: RoleInfo = {
    user: {
      name: 'Happyfe',
      avatar: 'https://happyfe.com/logo.png',
    },
    assistant: {
      name: 'DeepSeek',
      avatar: logo,
    },
    system: {
      name: 'System',
      avatar: 'https://lf3-static.bytednsdoc.com/obj/eden-cn/ptlz_zlp/ljhwZthlaukjlkulzlp/other/logo.png',
    },
  };

  const defaultMessage: ChatMessage[] = [
    {
      role: 'system',
      id: '1',
      createAt: 1715676751919,
      content: "Hello, I'm your AI assistant.",
    },
    {
      role: 'user',
      id: '2',
      createAt: 1715676751919,
      content: '给一个 Semi Design 的 Button 组件的使用示例',
    },
    {
      role: 'assistant',
      id: '3',
      createAt: 1715676751919,
      content: "以下是一个 Semi 代码的使用示例：\n\`\`\`jsx \nimport React from 'react';\nimport { Button } from '@kousum/semi-ui-vue';\n\nconst MyComponent = () => {\n  return (\n    <Button>Click me</Button>\n );\n};\nexport default MyComponent;\n\`\`\`\n",
    },
  ];

  let count = 0;
  const sendMessage = (message: string): void => {
    chats.value.push({
      role: 'user',
      id: count++,
      createAt: new Date().getTime(),
      content: message,
    });
    console.log(message);
    chats.value.push({
      role: 'assistant',
      id: count++,
      createAt: new Date().getTime(),
      content: '',
      status: 'loading',
    });

    const eventSource = new EventSource(`/api/stream?message=${message}`);
    eventSource.onmessage = (event: MessageEvent): void => {
      let response = event.data;
      console.log(response);
      if (response == 'end') {
        chats.value[chats.value.length - 1].status = 'complete';
        close();
        return;
      }
  
      response = JSON.parse(response).content;
      chats.value[chats.value.length - 1].status = 'incomplete';
      chats.value[chats.value.length - 1].content += response;
      console.log(chatRef.value.scrollToBottom);
      chatRef.value.scrollToBottom(true);
    };
  
    eventSource.onerror = (error: Event): void => {
      chats.value[chats.value.length - 1].status = 'error';
      close();
    };
  
    const close = (): void => {
      eventSource.close();
    };
  };
  </script>
  <style lang="scss" scoped></style>
  