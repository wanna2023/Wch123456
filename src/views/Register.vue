<template>
  <div class="register-container">
    <div class="register-box">
      <h2>用户注册</h2>
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
        <el-form-item prop="confirmPassword">
          <el-input
            v-model="formData.confirmPassword"
            type="password"
            placeholder="请确认密码"
            prefix-icon="Lock"
            show-password
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleRegister" :loading="loading" class="register-button">
            注册
          </el-button>
          <div class="login-link">
            已有账号？<router-link to="/login">立即登录</router-link>
          </div>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue';
import { ElMessage } from 'element-plus';
import { User, Lock } from '@element-plus/icons-vue';
import type { FormInstance, FormRules } from 'element-plus';
import { useRouter } from 'vue-router';
import axios from 'axios';
const router = useRouter();
const formRef = ref<FormInstance>();
const loading = ref(false);
const formData = reactive({
  username: '',
  password: '',
  confirmPassword: ''
});
// 密码验证函数
const validatePass = (rule: any, value: string, callback: Function) => {
  if (value === '') {
    callback(new Error('请输入密码'));
  } else {
    if (formData.confirmPassword !== '') {
      if (formRef.value) {
        formRef.value.validateField('confirmPassword');
      }
    }
    callback();
  }
};

// 确认密码验证函数
const validateConfirmPass = (rule: any, value: string, callback: Function) => {
  if (value === '') {
    callback(new Error('请再次输入密码'));
  } else if (value !== formData.password) {
    callback(new Error('两次输入密码不一致'));
  } else {
    callback();
  }
};
const rules: FormRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { validator: validatePass, trigger: 'blur' },
    { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
  ],
  confirmPassword: [
    { validator: validateConfirmPass, trigger: 'blur' }
  ]
};
const handleRegister = async () => {
  if (!formRef.value) return;
  try {
    loading.value = true;
    await formRef.value.validate();
    const response = await axios.post('http://127.0.0.1:8080/auth/register', {
      username: formData.username,
      password: formData.password
    }, {
      headers: { 'Content-Type': 'application/json' }
    });
    if (response.data.status === 'success') {
      ElMessage.success('注册成功');
      router.push('/login');
    } else {
      ElMessage.error(response.data.message || '注册失败，请稍后重试');
    }
  } catch (error: any) {
    console.error('注册错误:', error);
    ElMessage.error(error.response?.data?.message || '注册失败，请稍后重试');
  } finally {
    loading.value = false;
  }
};

</script>

<style lang="scss" scoped>
.register-container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);

  .register-box {
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

    .register-button {
      width: 100%;
      height: 42px;
      font-size: 1rem;
      background: linear-gradient(135deg, #4a90e2 0%, #357abd 100%);
      border: none;
      border-radius: 8px;
      transition: all 0.3s ease;

      &:hover {
        transform: translateY(-2px);
        box-shadow: 0 4px 12px rgba(74, 144, 226, 0.3);
      }

      &:active {
        transform: translateY(0);
      }
    }

    .login-link {
      text-align: center;
      margin-top: 1rem;
      font-size: 0.9rem;
      color: #606266;

      a {
        color: #4a90e2;
        text-decoration: none;
        font-weight: 500;

        &:hover {
          color: #357abd;
          text-decoration: underline;
        }
      }
    }
  }
}
</style>