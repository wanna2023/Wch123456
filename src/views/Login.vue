<template>
  <div class="login-container">
    <div class="login-box">
      <h2>欢迎登录</h2>
      <el-form :model="formData" :rules="rules" ref="formRef" @submit.prevent>
        <el-form-item prop="username">
          <el-input
            v-model="formData.username"
            placeholder="请输入用户名"
            prefix-icon="User"
          />
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="formData.password"
            type="password"
            placeholder="请输入密码"
            prefix-icon="Lock"
            show-password
          />
        </el-form-item>
        <el-form-item class="remember-me">
          <el-checkbox v-model="formData.remember">记住我</el-checkbox>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLogin" :loading="loading" class="login-button">
            登录
          </el-button>
          <div class="account-links">
            <router-link to="/register" class="register-link">注册账号</router-link>
            <router-link to="/forgot-password" class="forgot-password-link">忘记密码？</router-link>
          </div>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);

  .login-box {
    width: 100%;
    max-width: 400px;
    padding: 2rem;
    background: rgba(255, 255, 255, 0.9);
    border-radius: 16px;
    box-shadow: 0 8px 32px rgba(31, 38, 135, 0.15);
    backdrop-filter: blur(8px);
    transition: transform 0.3s ease;

    &:hover {
      transform: translateY(-5px);
    }

    h2 {
      text-align: center;
      color: #2c3e50;
      margin-bottom: 2rem;
      font-size: 1.8rem;
      font-weight: 600;
    }

    :deep(.el-input) {
      .el-input__wrapper {
        background: rgba(255, 255, 255, 0.8);
        border-radius: 8px;
        box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
        transition: all 0.3s ease;

        &:hover, &.is-focus {
          box-shadow: 0 4px 16px rgba(74, 144, 226, 0.15);
        }

        .el-input__inner {
          height: 42px;
          font-size: 1rem;
        }
      }
    }

    .remember-me {
      margin-bottom: 1rem;
    }

    .login-button {
      width: 100%;
      height: 42px;
      font-size: 1rem;
      background: linear-gradient(135deg, #4a90e2 0%, #357abd 100%);
      border: none;
      border-radius: 8px;
      transition: all 0.3s ease;
      margin-bottom: 1rem;

      &:hover {
        transform: translateY(-2px);
        box-shadow: 0 4px 12px rgba(74, 144, 226, 0.3);
      }

      &:active {
        transform: translateY(0);
      }
    }

    .account-links {
      display: flex;
      justify-content: space-between;
      align-items: center;
      font-size: 0.9rem;

      .register-link,
      .forgot-password-link {
        color: #4a90e2;
        text-decoration: none;
        transition: color 0.3s ease;

        &:hover {
          color: #357abd;
          text-decoration: underline;
        }
      }
    }
  }
}
</style>


<!-- 注释掉多余的script setup标签 -->
<script setup lang="ts">

import { ref, reactive } from 'vue'; // 导入Vue的响应式API
import { ElMessage } from 'element-plus'; // 导入Element Plus的消息组件
import { useRouter } from 'vue-router';
import { User, Lock } from '@element-plus/icons-vue'; // 导入Element Plus的图标
import type { FormInstance, FormRules } from 'element-plus'; // 导入Element Plus的类型定义
import axios from 'axios';

// 定义表单引用，用于访问表单实例
const formRef = ref<FormInstance>();

// 定义加载状态
const loading = ref(false);

const router = useRouter(); // 初始化路由实例

// 定义表单数据
const formData = reactive({
  username: '', // 用户名
  password: '', // 密码
  remember: false // 记住我选项
});

// 定义表单验证规则
const rules: FormRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }, // 必填验证
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' } // 长度验证
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }, // 必填验证
    { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' } // 长度验证
  ]
};

const handleLogin = async () => {
  if (!formRef.value) return;
  
  try {
    loading.value = true;
    await formRef.value.validate();
    
    // 输出表单数据，确保数据正确传递
    console.log('Form Data:', formData);
    
    const response = await axios.post('http://127.0.0.1:8080/auth/login', formData, {
      headers: { 'Content-Type': 'application/json' }
    });
    
    if (response.data.status === 'success') {
      ElMessage.success('登录成功');
      router.push('/index');
      // TODO: 处理成功逻辑
    } else {
      ElMessage.error('登录失败，请检查用户名或密码');
    }
  } catch (error) {
    console.error('请求错误:', error);
    ElMessage.error('登录失败，请稍后重试');
  } finally {
    loading.value = false;
  }
};

</script>