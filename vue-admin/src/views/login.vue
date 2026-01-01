<template>
  <div class="login-container">
    <div class="login-box">
      <div class="login-banner">
        <div class="banner-content">
          <h2 class="banner-title">{{ title }}</h2>
        </div>
      </div>

      <div class="login-form">
        <div class="form-header">
          <h3 class="title">管理员登录</h3>
          <p class="subtitle">请输入您的登录信息</p>
        </div>

        <el-form ref="loginRef" :model="loginForm" :rules="loginRules" class="form-content">
          <el-form-item prop="username">
            <el-input
              v-model="loginForm.username"
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
              v-model="loginForm.password"
              type="password"
              size="large"
              auto-complete="off"
              placeholder="请输入密码"
              @keyup.enter="handleLogin"
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
                  v-model="loginForm.code"
                  size="large"
                  auto-complete="off"
                  placeholder="请输入验证码"
                  @keyup.enter="handleLogin"
                >
                  <template #prefix>
                    <svg-icon icon-class="validCode" class="el-input__icon input-icon" />
                  </template>
                </el-input>
              </el-col>
              <el-col :span="10">
                <div class="login-code">
                  <img :src="codeUrl" @click="getCode" class="login-code-img" alt="验证码"/>
                </div>
              </el-col>
            </el-row>
          </el-form-item>

          <el-form-item>
            <el-row>
              <el-col :span="12">
                <el-checkbox v-model="loginForm.rememberMe">记住密码</el-checkbox>
              </el-col>
            </el-row>
          </el-form-item>

          <el-form-item style="width:100%;">
            <el-button
              :loading="loading"
              size="large"
              type="primary"
              style="width:100%;"
              @click.prevent="handleLogin"
            >
              <span v-if="!loading">登录</span>
              <span v-else>登录中...</span>
            </el-button>
          </el-form-item>

          <div class="register-link" v-if="register">
            还没有账户？
            <router-link class="link-type" :to="'/register'">立即注册</router-link>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { getCodeImg } from "@/api/login"
import Cookies from "js-cookie"
import { encrypt, decrypt } from "@/utils/jsencrypt"
import useUserStore from '@/store/modules/user'

const title = import.meta.env.VITE_APP_TITLE
const userStore = useUserStore()
const route = useRoute()
const router = useRouter()
const { proxy } = getCurrentInstance()

const loginForm = ref({
  username: "",
  password: "",
  rememberMe: false,
  code: "",
  uuid: ""
})

const loginRules = {
  username: [{ required: true, trigger: "blur", message: "请输入您的账号" }],
  password: [{ required: true, trigger: "blur", message: "请输入您的密码" }],
  code: [{ required: true, trigger: "change", message: "请输入验证码" }]
}

const codeUrl = ref("")
const loading = ref(false)
// 验证码开关
const captchaEnabled = ref(false)
// 注册开关
const register = ref(false)
const redirect = ref(undefined)

watch(route, (newRoute) => {
    redirect.value = newRoute.query && newRoute.query.redirect
}, { immediate: true })

function handleLogin() {
  proxy.$refs.loginRef.validate(valid => {
    if (valid) {
      loading.value = true
      // 勾选了需要记住密码设置在 cookie 中设置记住用户名和密码
      if (loginForm.value.rememberMe) {
        Cookies.set("username", loginForm.value.username, { expires: 30 })
        Cookies.set("password", encrypt(loginForm.value.password), { expires: 30 })
        Cookies.set("rememberMe", loginForm.value.rememberMe, { expires: 30 })
      } else {
        // 否则移除
        Cookies.remove("username")
        Cookies.remove("password")
        Cookies.remove("rememberMe")
      }
      // 调用action的登录方法
      userStore.login(loginForm.value).then(() => {
        const query = route.query
        const otherQueryParams = Object.keys(query).reduce((acc, cur) => {
          if (cur !== "redirect") {
            acc[cur] = query[cur]
          }
          return acc
        }, {})
        router.push({ path: redirect.value || "/", query: otherQueryParams })
      }).catch(() => {
        loading.value = false
        // 重新获取验证码
        if (captchaEnabled.value) {
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
      loginForm.value.uuid = res.uuid
    }
  })
}

function getCookie() {
  const username = Cookies.get("username")
  const password = Cookies.get("password")
  const rememberMe = Cookies.get("rememberMe")
  loginForm.value = {
    username: username === undefined ? loginForm.value.username : username,
    password: password === undefined ? loginForm.value.password : decrypt(password),
    rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
  }
}

getCode()
getCookie()
</script>

<style lang='scss' scoped>
.login-container {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
  min-height: 600px;
  background: url('@/assets/images/login-background.jpg') no-repeat center center;
  background-size: cover;
  position: relative;

  .login-box {
    display: flex;
    width: 900px;
    height: 500px;
    background: #fff;
    border-radius: 10px;
    box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
    overflow: hidden;

    .login-banner {
      flex: 1;
      background-color: #4e9af8;
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

    .login-form {
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

        :deep(.el-checkbox) {
          color: #666;
        }
      }

      .login-code {
        width: 100%;
        height: 46px;
        display: flex;
        align-items: center;
        justify-content: center;
        background: #f5f5f5;
        border-radius: 4px;
        cursor: pointer;

        .login-code-img {
          max-width: 100%;
          max-height: 100%;
        }
      }

      .text-right {
        text-align: right;
      }

      .register-link {
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

  .login-footer {
    position: absolute;
    bottom: 20px;
    width: 100%;
    text-align: center;
    color: rgba(255, 255, 255, 0.8);
    font-size: 14px;
  }

  @media (max-width: 950px) {
    .login-box {
      width: 90%;
      max-width: 500px;

      .login-banner {
        display: none;
      }
    }
  }
}
</style>
