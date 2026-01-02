<template>
  <!-- 页面容器 -->
  <div class="master-detail-page">
    <div class="detail-container">
      <!-- 返回按钮 -->
      <div class="back-button">
        <el-button @click="router.go(-1)" type="primary">
          <el-icon>
            <Back/>
          </el-icon>
          返回列表
        </el-button>
      </div>

      <!-- 详情卡片 -->
      <div class="master-detail-card">
        <div class="master-header">
          <!-- 照片 -->
          <div class="master-image">
            <img :src="baseUrl + master.image" alt="">
          </div>
          <!-- 基本信息 -->
          <div class="master-basic-info">
            <h1 class="master-name">{{ master.name }}</h1>
            <p class="master-location">
              <el-icon>
                <Location/>
              </el-icon>
              {{ master.location }}
            </p>
          </div>
        </div>

        <div class="master-details">
          <div class="tab-content">
            <h3>技艺专长</h3>
            <p class="skills-skills">{{ master.skills }}</p>

            <h3>传承贡献</h3>
            <el-timeline>
              <el-timeline-item type="success"
                                v-for="(contribution, index) in master.masterContributionList"
                                :key="contribution.contributionId"
                                :timestamp="'贡献' + (index + 1)"
                                placement="top"
              >
                <el-card>
                  <p>{{ contribution.content }}</p>
                </el-card>
              </el-timeline-item>

            </el-timeline>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<script setup>
import {onMounted, ref} from 'vue'
import {useRoute, useRouter} from "vue-router";
import {getMaster} from "@/api/ich/master.js";
import {Back, Location} from "@element-plus/icons-vue";

//基础URL
const baseUrl = import.meta.env.VITE_APP_BASE_API

const route = useRoute()
const router = useRouter()

//传承人数据
const master = ref({})

//组件挂载后执行
onMounted(() => {
  getMaster(route.params.id).then(res => {
    master.value = res.data
  })
})

</script>

<style scoped>
/* 页面主容器样式 */
.master-detail-page {
  width: 100%; /* 宽度100% */
  background-color: #f5f5f5; /* 浅灰色背景 */
  min-height: 100vh; /* 最小高度为视口高度，确保占满整个屏幕 */
  padding: 20px 0; /* 上下内边距20px，左右无内边距 */
}

/* 详情内容容器 */
.detail-container {
  max-width: 1200px; /* 最大宽度1200px，在大屏幕上不会过宽 */
  margin: 0 auto; /* 水平居中 */
  padding: 0 20px; /* 左右内边距20px */
}

/* 返回按钮区域样式 */
.back-button {
  margin-bottom: 20px; /* 底部外边距20px，与下方内容分隔 */
}

/* 传承人详情卡片样式 */
.master-detail-card {
  background: white; /* 白色背景 */
  border-radius: 15px; /* 圆角边框 */
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.08); /* 阴影效果，增加立体感 */
  overflow: hidden; /* 隐藏溢出内容，确保圆角效果 */
}

/* 头部区域样式 */
.master-header {
  display: flex; /* 使用flex布局 */
  padding: 40px; /* 内边距40px */
  /* 渐变背景：从左上的浅灰色到右下的浅蓝色 */
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
}

/* 头像容器样式 */
.master-image {
  width: 200px; /* 固定宽度 */
  height: 200px; /* 固定高度，形成正方形 */
  border-radius: 50%; /* 圆形效果 */
  overflow: hidden; /* 隐藏溢出部分，确保圆形 */
  border: 5px solid white; /* 白色边框 */
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.1); /* 阴影效果 */
}

/* 头像图片样式 */
.master-image img {
  width: 100%; /* 宽度100%填充容器 */
  height: 100%; /* 高度100%填充容器 */
  object-fit: cover; /* 保持图片比例，覆盖整个容器 */
}

/* 基本信息区域样式 */
.master-basic-info {
  flex: 1; /* 占据剩余空间 */
  padding-left: 40px; /* 左内边距40px，与头像分隔 */
  display: flex; /* flex布局 */
  flex-direction: column; /* 垂直方向排列 */
  justify-content: center; /* 垂直居中 */
}

/* 姓名样式 */
.master-name {
  font-size: 36px; /* 大字体 */
  font-weight: bold; /* 粗体 */
  margin: 0 0 15px 0; /* 底部外边距15px，其他方向无外边距 */
  color: #212529; /* 深灰色文字 */
}

/* 所在地样式 */
.master-location {
  display: flex; /* flex布局 */
  align-items: center; /* 垂直居中 */
  font-size: 18px; /* 中等字体 */
  color: #6c757d; /* 中灰色文字 */
}

/* 所在地图标样式 */
.master-location i {
  margin-right: 10px; /* 图标右边距10px，与文字分隔 */
  color: #8B4513; /* 棕色图标 */
}

/* 详细信息区域样式 */
.master-details {
  padding: 30px 40px; /* 内边距 */
}

/* 标题样式 */
.tab-content h3 {
  font-size: 22px; /* 较大字体 */
  color: #212529; /* 深灰色 */
  margin: 20px 0 15px; /* 上下外边距，底部稍小 */
}

/* 技艺专长文字样式 */
.skills-skills {
  font-size: 16px; /* 正常字体大小 */
  line-height: 1.8; /* 行高1.8，增加可读性 */
  color: #555; /* 中灰色文字 */
  margin-bottom: 20px; /* 底部外边距 */
}

/* 经验内容段落样式 */
.experience-content p {
  margin-bottom: 15px; /* 段落间距 */
}

/* 最后一个段落无底部外边距 */
.experience-content p:last-child {
  margin-bottom: 0;
}

/* 加载容器样式 */
.loading-container {
  background: white; /* 白色背景 */
  border-radius: 15px; /* 圆角 */
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.08); /* 阴影 */
  padding: 40px; /* 内边距 */
}
</style>
