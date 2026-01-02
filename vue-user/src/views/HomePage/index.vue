<template>
  <div>
    <!--头部-->
    <div class="header-container">
      <div class="header">
        <div class="header-left">
          <img src="@/assets/logo/logo.png" alt="">
          <div class="titles" @click="router.push('/index/home')">非物质文化遗产系统</div>
        </div>
        <div class="header-center">
          <div class="front-header-nav">
            <el-menu :default-active="route.path" mode="horizontal" :router="true">
              <el-menu-item index="/index/home">
                <el-icon>
                  <HomeFilled/>
                </el-icon>
                首页
              </el-menu-item>
              <el-menu-item index="/index/heritage">
                <el-icon>
                  <Collection/>
                </el-icon>
                非遗展示
              </el-menu-item>
              <el-menu-item index="/index/news">
                <el-icon>
                  <Document/>
                </el-icon>
                新闻资讯
              </el-menu-item>
              <el-menu-item index="/index/projects">
                <el-icon>
                  <FolderAdd/>
                </el-icon>
                项目申报
              </el-menu-item>
              <el-menu-item index="/index/profile">
                <el-icon>
                  <UserFilled/>
                </el-icon>
                个人中心
              </el-menu-item>
            </el-menu>
          </div>
        </div>
        <div class="header-right">
          <div v-if="!nickName" class="auth-buttons">
            <el-button @click="router.push('/login')">登录</el-button>
            <el-button @click="router.push('/register')">注册</el-button>
          </div>
          <div v-else class="user-info">
            <el-dropdown>
              <div class="header-dropdown">
                <img :src="avatar" alt="">
                <div class="user-name">
                  <span>{{ nickName }}</span><i class="el-icon-arrow-down"
                                                style="margin-left: 5px"></i>
                </div>
              </div>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item>
                    <el-button type="text" style="text-decoration: none" @click.native="logout">
                      退出
                    </el-button>
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </div>
      </div>
    </div>

    <div>
      <router-view/>
    </div>
  </div>
</template>

<script setup>
import {ref, computed, onMounted} from 'vue'
import {useRoute, useRouter} from 'vue-router'
import {getUser} from "@/api/system/user"
import useUserStore from "@/store/modules/user.js";
import {ElMessageBox} from "element-plus";
import {Collection, Document, FolderAdd, HomeFilled, UserFilled} from "@element-plus/icons-vue";

const userStore = useUserStore()
const route = useRoute()
const router = useRouter()

const nickName = ref(null)
const top = ref('')
const title = ref(route.query.title)

const avatar = computed(() => userStore.avatar)

onMounted(() => {
  getList()
})

const getList = () => {
  getUser(userStore.id).then(res => {
    nickName.value = res.data.nickName
  })
}

const logout = () => {
  ElMessageBox.confirm('确定注销并退出系统吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    userStore.logOut().then(() => {
      location.href = '/index'
    })
  }).catch(() => {
  })
}
</script>

<style scoped>
.front-header-nav .el-menu-item {
  font-size: 16px;
  font-weight: 500;
}

.front-header-nav .el-menu--horizontal .el-menu-item:not(.is-disabled):hover {
  color: #409EFF;
  background-color: #ecf5ff;
}

.front-header-nav .el-menu--horizontal > .el-menu-item.is-active {
  color: #409EFF;
  border-bottom: 2px solid #409EFF;
}

.front-header-nav .el-menu-item .el-icon {
  margin-right: 5px;
  width: 1em;
  height: 1em;
  vertical-align: middle;
}
</style>
