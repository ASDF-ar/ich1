<template>
  <div class="register-container">
    <div class="register-box">
      <div class="register-banner">
        <div class="banner-content">
          <h2 class="banner-title">欢迎加入{{ title }}</h2>
        </div>
      </div>

      <div class="register-form">
        <div class="form-header">
          <h3 class="title">注册</h3>
          <p class="subtitle">请填写以下信息创建账户</p>
        </div>

        <el-form ref="registerRef" :model="registerForm" :rules="registerRules" class="form-content">
          <el-form-item prop="username">
            <el-input
              v-model="registerForm.username"
              type="text"
              size="large"
              auto-complete="off"
              placeholder="请输入用户名"
            >
              <template #prefix>
                <svg-icon icon-class="user" class="el-input__icon input-icon" />
              </template>
            </el-input>
          </el-form-item>

          <el-form-item prop="password">
            <el-input
              v-model="registerForm.password"
              type="password"
              size="large"
              auto-complete="off"
              placeholder="请输入密码"
              @keyup.enter="handleRegister"
            >
              <template #prefix>
                <svg-icon icon-class="password" class="el-input__icon input-icon" />
              </template>
            </el-input>
          </el-form-item>

          <el-form-item prop="confirmPassword">
            <el-input
              v-model="registerForm.confirmPassword"
              type="password"
              size="large"
              auto-complete="off"
              placeholder="请确认密码"
              @keyup.enter="handleRegister"
            >
              <template #prefix>
                <svg-icon icon-class="password" class="el-input__icon input-icon" />
              </template>
            </el-input>
          </el-form-item>

          <el-form-item prop="code" v-if="captchaEnabled">
            <el-row :gutter="15">
              <el-col :span="14">
                <el-input
                  size="large"
                  v-model="registerForm.code"
                  auto-complete="off"
                  placeholder="请输入验证码"
                  @keyup.enter="handleRegister"
                >
                  <template #prefix>
                    <svg-icon icon-class="validCode" class="el-input__icon input-icon" />
                  </template>
                </el-input>
              </el-col>
              <el-col :span="10">
                <div class="register-code">
                  <img :src="codeUrl" @click="getCode" class="register-code-img" alt="验证码"/>
                </div>
              </el-col>
            </el-row>
          </el-form-item>

          <el-form-item style="width:100%;">
            <el-button
              :loading="loading"
              size="large"
              type="primary"
              style="width:100%;"
              @click.prevent="handleRegister"
            >
              <span v-if="!loading">注册</span>
              <span v-else>注册中...</span>
            </el-button>
          </el-form-item>

          <div class="login-link">
            已有账户？
            <router-link class="link-type" :to="'/login'">立即登录</router-link>
          </div>
        </el-form>
      </div>
    </div>

    <div class="register-footer">
      <p>© 2023 {{ title }} - 专业的企业级管理系统</p>
    </div>
  </div>
</template>

<script setup>
import { ElMessageBox } from "element-plus"
import { getCodeImg, register } from "@/api/login"
import {useRouter} from "vue-router";

const title = import.meta.env.VITE_APP_TITLE
const router = useRouter()
const { proxy } = getCurrentInstance()

const registerForm = ref({
  username: "",
  password: "",
  confirmPassword: "",
  code: "",
  uuid: ""
})

const equalToPassword = (rule, value, callback) => {
  if (registerForm.value.password !== value) {
    callback(new Error("两次输入的密码不一致"))
  } else {
    callback()
  }
}

const registerRules = {
  username: [
    { required: true, trigger: "blur", message: "请输入您的账号" },
    { min: 2, max: 20, message: "用户账号长度必须介于 2 和 20 之间", trigger: "blur" }
  ],
  password: [
    { required: true, trigger: "blur", message: "请输入您的密码" },
    { min: 5, max: 20, message: "用户密码长度必须介于 5 和 20 之间", trigger: "blur" },
    { pattern: /^[^<>"'|\\]+$/, message: "不能包含非法字符：< > \" ' \\\ |", trigger: "blur" }
  ],
  confirmPassword: [
    { required: true, trigger: "blur", message: "请再次输入您的密码" },
    { required: true, validator: equalToPassword, trigger: "blur" }
  ],
  code: [{ required: true, trigger: "change", message: "请输入验证码" }]
}

const codeUrl = ref("")
const loading = ref(false)
const captchaEnabled = ref(true)

function handleRegister() {
  proxy.$refs.registerRef.validate(valid => {
    if (valid) {
      loading.value = true
      register(registerForm.value).then(res => {
        const username = registerForm.value.username
        ElMessageBox.alert("<font color='red'>恭喜你，您的账号 " + username + " 注册成功！</font>", "系统提示", {
          dangerouslyUseHTMLString: true,
          type: "success",
        }).then(() => {
          router.push("/login")
        }).catch(() => {})
      }).catch(() => {
        loading.value = false
        if (captchaEnabled) {
          getCode()
        }
      })
    }
  })
}

function getCode() {
  getCodeImg().then(res => {
    captchaEnabled.value = res.captchaEnabled === undefined ? true : res.captchaEnabled
    if (captchaEnabled.value) {
      codeUrl.value = "data:image/gif;base64," + res.img
      registerForm.value.uuid = res.uuid
    }
  })
}

getCode()
</script>

<style lang='scss' scoped>
.register-container {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
  min-height: 600px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  position: relative;

  .register-box {
    display: flex;
    width: 900px;
    height: 550px;
    background: #fff;
    border-radius: 10px;
    box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
    overflow: hidden;

    .register-banner {
      flex: 1;
      background: linear-gradient(120deg, #f093fb, #f5576c);
      color: white;
      display: flex;
      align-items: center;
      justify-content: center;
      position: relative;
      overflow: hidden;

      &::before {
        content: "";
        position: absolute;
        width: 300px;
        height: 300px;
        border-radius: 50%;
        background: rgba(255, 255, 255, 0.1);
        top: -100px;
        right: -100px;
      }

      &::after {
        content: "";
        position: absolute;
        width: 200px;
        height: 200px;
        border-radius: 50%;
        background: rgba(255, 255, 255, 0.1);
        bottom: -80px;
        left: -80px;
      }

      .banner-content {
        position: relative;
        z-index: 1;
        text-align: center;
        padding: 30px;

        .banner-title {
          font-size: 32px;
          font-weight: 600;
          margin-bottom: 15px;
          letter-spacing: 1px;
        }

        .banner-subtitle {
          font-size: 16px;
          opacity: 0.9;
        }
      }
    }

    .register-form {
      flex: 1;
      padding: 40px;
      display: flex;
      flex-direction: column;
      justify-content: center;

      .form-header {
        text-align: center;
        margin-bottom: 30px;

        .title {
          font-size: 28px;
          font-weight: 600;
          color: #333;
          margin-bottom: 10px;
        }

        .subtitle {
          font-size: 14px;
          color: #999;
        }
      }

      .form-content {
        :deep(.el-form-item) {
          margin-bottom: 22px;
        }

        :deep(.el-input__inner) {
          height: 46px;
          line-height: 46px;
          padding-left: 40px;
          border-radius: 4px;
        }

        :deep(.input-icon) {
          height: 46px;
          width: 16px;
          margin-left: 12px;
        }
      }

      .register-code {
        width: 100%;
        height: 46px;
        display: flex;
        align-items: center;
        justify-content: center;
        background: #f5f5f5;
        border-radius: 4px;
        cursor: pointer;

        .register-code-img {
          max-width: 100%;
          max-height: 100%;
        }
      }

      .login-link {
        text-align: center;
        margin-top: 20px;
        color: #666;

        .link-type {
          color: #409eff;
          text-decoration: none;
          margin-left: 5px;

          &:hover {
            text-decoration: underline;
          }
        }
      }
    }
  }

  .register-footer {
    position: absolute;
    bottom: 20px;
    width: 100%;
    text-align: center;
    color: rgba(255, 255, 255, 0.8);
    font-size: 14px;
  }

  @media (max-width: 950px) {
    .register-box {
      width: 90%;
      max-width: 500px;

      .register-banner {
        display: none;
      }
    }
  }
}
</style>
