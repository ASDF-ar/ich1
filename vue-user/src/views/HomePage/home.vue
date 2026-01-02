<template>
  <div class="home-page">
    <!-- 轮播图区域 -->
    <div class="banner-section">
      <el-carousel height="500px" indicator-position="outside">
        <el-carousel-item v-for="banner in bannerList" :key="banner.bannerId">
          <div class="carousel-item" :style="{ backgroundImage: `url(${baseUrl + banner.image})` }">
            <div class="carousel-content">
              <h1 class="carousel-title">{{ banner.title }}</h1>
              <p class="carousel-desc">{{ banner.description }}</p>
            </div>
          </div>
        </el-carousel-item>
      </el-carousel>
    </div>

    <!-- 非遗项目展示区域 -->
    <div class="featured-section section-container">
      <div class="section-header">
        <h2 class="section-title">非遗项目</h2>
        <el-button category="text" @click="goToAllHeritage">查看全部</el-button>
      </div>

      <div class="featured-grid">
        <div v-for="item in heritageList"
             :key="item.heritageId"
             class="featured-card"
             @click="goToDetail(item.heritageId)"
        >
          <div class="card-image">
            <img :src="baseUrl + item.image" alt="">
          </div>

          <div class="card-content">
            <h3 class="card-title">{{ item.title }}</h3>
            <p class="card-desc">{{ item.description }}</p>
            <div class="card-meta">
              <!-- 地理位置 -->
              <span class="card-location">
                                <el-icon><LocationFilled/></el-icon>
                                {{ item.location }}
                            </span>
              <!-- 项目类别 -->
              <span class="card-category">{{ item.category }}</span>
            </div>
          </div>

        </div>
      </div>

    </div>

  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue'
import {listBanner} from "@/api/ich/banner.js";
import {useRouter} from "vue-router";
import {listHeritage} from "@/api/ich/heritage.js";
import {LocationFilled} from "@element-plus/icons-vue";

const baseUrl = import.meta.env.VITE_APP_BASE_API

const router = useRouter()

//查询参数
const query = ref({
  pageNum: 1,
  pageSize: 6,
})

//轮播图数据
const bannerList = ref([])

//非遗项目列表数据
const heritageList = ref([])

//查看非遗项目详情
const goToDetail = (heritageId) => {
  router.push('/index/heritageDetail/' + heritageId)
}

onMounted(() => {
  listBanner().then(res => {
    bannerList.value = res.rows
  })

  listHeritage(query.value).then(res => {
    heritageList.value = res.rows
  })
})

//跳转到非遗展示页面
const goToAllHeritage = () => {
  router.push('/index/heritage')
}

</script>

<style scoped>
/* 首页整体样式 */
.home-page {
  width: 100%; /* 占据全部宽度 */
}

/* 区域容器通用样式 */
.home-page .section-container {
  max-width: 1200px; /* 最大宽度限制 */
  margin: 0 auto; /* 水平居中 */
  padding: 40px 20px; /* 内边距 */
}

/* 区域标题通用样式 */
.home-page .section-title {
  text-align: center; /* 文字居中 */
  font-size: 28px; /* 字体大小 */
  font-weight: bold; /* 字体粗细 */
  margin-bottom: 30px; /* 底部外边距 */
  color: #333; /* 文字颜色 */
  position: relative; /* 相对定位，用于伪元素定位 */
}

/* 区域标题下方的装饰线 */
.home-page .section-title::after {
  content: ''; /* 伪元素内容 */
  display: block; /* 块级显示 */
  width: 60px; /* 宽度 */
  height: 3px; /* 高度 */
  background: linear-gradient(90deg, #3a7bd5, #00d2ff); /* 渐变背景 */
  margin: 10px auto 0; /* 外边距：上 10px，左右自动，下 0 */
  border-radius: 3px; /* 圆角 */
}

/* 区域头部布局 */
.home-page .section-header {
  display: flex; /* 弹性布局 */
  justify-content: space-between; /* 两端对齐 */
  align-items: center; /* 垂直居中 */
  margin-bottom: 30px; /* 底部外边距 */
}

/* 轮播图区域样式 */
.banner-section {
  max-width: 1200px; /* 最大宽度限制 */
  margin: 0 auto; /* 水平居中 */
  padding: 20px; /* 内边距 */
}

/* 轮播图容器样式 */
.banner-section .el-carousel {
  border-radius: 10px; /* 圆角 */
  overflow: hidden; /* 溢出隐藏 */
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); /* 阴影效果 */
}

/* 深度选择器修改 Element Plus 轮播图指示器样式 */
.banner-section .el-carousel :deep(.el-carousel__indicator) .el-carousel__button {
  width: 30px; /* 指示器宽度 */
  height: 3px; /* 指示器高度 */
  border-radius: 0; /* 去除圆角 */
}

/* 激活状态指示器样式 */
.banner-section .el-carousel :deep(.el-carousel__indicator).is-active .el-carousel__button {
  background-color: #3a7bd5; /* 激活状态颜色 */
}

/* 轮播图容器圆角 */
.banner-section .el-carousel :deep(.el-carousel__container) {
  border-radius: 10px; /* 圆角 */
}

/* 轮播项样式 */
.banner-section .carousel-item {
  width: 100%; /* 宽度100% */
  height: 100%; /* 高度100% */
  background-size: cover; /* 背景图覆盖 */
  background-position: center; /* 背景图居中 */
  display: flex; /* 弹性布局 */
  align-items: center; /* 垂直居中 */
  position: relative; /* 相对定位 */
}

/* 轮播项遮罩层 - 增强文字可读性 */
.banner-section .carousel-item::before {
  content: ''; /* 伪元素内容 */
  position: absolute; /* 绝对定位 */
  top: 0; /* 顶部对齐 */
  left: 0; /* 左侧对齐 */
  right: 0; /* 右侧对齐 */
  bottom: 0; /* 底部对齐 */
  background: rgba(0, 0, 0, 0.4); /* 半透明黑色背景 */
}

/* 轮播内容区域样式 */
.banner-section .carousel-content {
  position: relative; /* 相对定位 */
  z-index: 1; /* 层级高于遮罩层 */
  color: white; /* 文字颜色 */
  text-align: center; /* 文字居中 */
  width: 100%; /* 宽度100% */
  padding: 0 20px; /* 左右内边距 */
}

/* 轮播标题样式 */
.banner-section .carousel-content .carousel-title {
  font-size: 42px; /* 字体大小 */
  font-weight: bold; /* 字体粗细 */
  margin-bottom: 20px; /* 底部外边距 */
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5); /* 文字阴影 */
}

/* 轮播描述样式 */
.banner-section .carousel-content .carousel-desc {
  font-size: 20px; /* 字体大小 */
  margin-bottom: 30px; /* 底部外边距 */
  max-width: 800px; /* 最大宽度 */
  margin-left: auto; /* 左侧自动外边距 */
  margin-right: auto; /* 右侧自动外边距 */
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.5); /* 文字阴影 */
}

/* 非遗精品区域样式 */
.featured-section .featured-grid {
  display: grid; /* 网格布局 */
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); /* 自适应列数，最小280px */
  gap: 30px; /* 网格间距 */
}

/* 非遗卡片样式 */
.featured-section .featured-card {
  border-radius: 15px; /* 圆角 */
  overflow: hidden; /* 溢出隐藏 */
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.08); /* 阴影效果 */
  transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.1); /* 过渡动画，贝塞尔曲线 */
  cursor: pointer; /* 鼠标指针样式 */
  background: white; /* 背景色 */
  position: relative; /* 相对定位 */
}

/* 卡片悬停效果 */
.featured-section .featured-card:hover {
  transform: translateY(-10px) scale(1.02); /* 上移和轻微放大 */
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.15); /* 增强阴影 */
}

/* 悬停时显示遮罩层 */
.featured-section .featured-card:hover .overlay {
  opacity: 1; /* 不透明度为1，完全显示 */
}

/* 悬停时图片放大效果 */
.featured-section .featured-card:hover .card-image img {
  transform: scale(1.1); /* 图片放大1.1倍 */
}

/* 卡片图片区域样式 */
.featured-section .featured-card .card-image {
  height: 200px; /* 固定高度 */
  overflow: hidden; /* 溢出隐藏 */
  position: relative; /* 相对定位 */
}

/* 卡片图片样式 */
.featured-section .featured-card .card-image img {
  width: 100%; /* 宽度100% */
  height: 100%; /* 高度100% */
  object-fit: cover; /* 图片覆盖，保持比例 */
  transition: transform 0.5s ease; /* 变换过渡效果 */
}

/* 遮罩层样式 */
.featured-section .featured-card .card-image .overlay {
  position: absolute; /* 绝对定位 */
  top: 0; /* 顶部对齐 */
  left: 0; /* 左侧对齐 */
  right: 0; /* 右侧对齐 */
  bottom: 0; /* 底部对齐 */
  background: rgba(225, 198, 110, 0.7); /* 半透明金色背景 */
  display: flex; /* 弹性布局 */
  align-items: center; /* 垂直居中 */
  justify-content: center; /* 水平居中 */
  opacity: 0; /* 默认隐藏 */
  transition: opacity 0.4s ease; /* 不透明度过渡效果 */
  z-index: 1; /* 层级 */
}

/* 遮罩内容样式 */
.featured-section .featured-card .card-image .overlay .overlay-content {
  color: white; /* 文字颜色 */
  text-align: center; /* 文字居中 */
  font-size: 18px; /* 字体大小 */
  font-weight: 500; /* 字体粗细 */
}

/* 遮罩图标样式 */
.featured-section .featured-card .card-image .overlay .overlay-content i {
  display: block; /* 块级显示 */
  font-size: 24px; /* 字体大小 */
  margin-bottom: 8px; /* 底部外边距 */
}

/* 卡片内容区域样式 */
.featured-section .featured-card .card-content {
  padding: 25px; /* 内边距 */
}

/* 卡片标题样式 */
.featured-section .featured-card .card-content .card-title {
  font-size: 20px; /* 字体大小 */
  font-weight: 700; /* 字体粗细 */
  margin: 0 0 12px 0; /* 外边距：上右下左 */
  color: #212529; /* 文字颜色 */
  line-height: 1.3; /* 行高 */
}

/* 卡片描述样式 */
.featured-section .featured-card .card-content .card-desc {
  color: #6c757d; /* 文字颜色 */
  font-size: 15px; /* 字体大小 */
  line-height: 1.6; /* 行高 */
  margin-bottom: 20px; /* 底部外边距 */
  display: -webkit-box; /* 使用Webkit盒子模型 */
  -webkit-line-clamp: 2; /* 限制显示2行 */
  -webkit-box-orient: vertical; /* 垂直方向 */
  overflow: hidden; /* 溢出隐藏 */
}

/* 卡片元信息布局 */
.featured-section .featured-card .card-content .card-meta {
  display: flex; /* 弹性布局 */
  justify-content: space-between; /* 两端对齐 */
  align-items: center; /* 垂直居中 */
}

/* 位置和分类标签通用样式 */
.featured-section .featured-card .card-content .card-meta .card-location,
.featured-section .featured-card .card-content .card-meta .card-category {
  font-size: 14px; /* 字体大小 */
  padding: 6px 12px; /* 内边距 */
  border-radius: 20px; /* 圆角，形成胶囊形状 */
  background-color: #f8f9fa; /* 背景色 */
  color: #495057; /* 文字颜色 */
  display: flex; /* 弹性布局 */
  align-items: center; /* 垂直居中 */
  gap: 5px; /* 子元素间距 */
}

/* 位置标签图标颜色 */
.featured-section .featured-card .card-content .card-meta .card-location i {
  color: #8B4513; /* 棕色图标 */
}

/* 分类标签特殊样式 */
.featured-section .featured-card .card-content .card-meta .card-category {
  background-color: #D9A37D; /* 浅棕色背景 */
  color: #495057; /* 文字颜色 */
  font-weight: 500; /* 字体粗细 */
}
</style>
