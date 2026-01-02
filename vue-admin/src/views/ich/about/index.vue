<template>
  <div class="about-page">
    <!-- 创建数据按钮 -->
    <div class="button-container" v-if="!isExist">
      <el-button type="primary" @click="createData" class="create-button">
        创建数据
      </el-button>
    </div>

    <!-- 实时编辑区域, 当有数据时显示 -->
    <div class="section-container" v-else>
      <!-- 系统介绍部分 -->
      <div class="intro-section">
        <div class="intro-content">
          <div class="section-title">非物质文化遗产保护</div>
          <div class="editable-wrapper">
            <div class="intro-text" @click="editContent('intro')" v-if="!isEditing('intro')">
              <p>{{ aboutForm.intro }}</p>
              <!-- 编辑按钮 -->
              <el-button class="edit-button" type="primary" link>
                <el-icon>
                  <Edit/>
                </el-icon>
              </el-button>
            </div>
            <!-- 编辑模式 -->
            <el-input v-else
                      v-model="aboutForm.intro"
                      type="textarea"
                      :rows="6"
                      @blur="finishEditing()"
            />
          </div>
        </div>
        <!-- 图片上传 -->
        <div class="intro-image">
          <image-upload v-model="aboutForm.image"/>
        </div>
      </div>

      <!-- 使命愿景部分 -->
      <div class="mission-section">
        <div class="mission-card">
          <div class="mission-icon">🎯</div>
          <h3 class="mission-title">我们的使命</h3>
          <div class="editable-wrapper">
            <p class="mission-text" @click="editContent('mission')" v-if="!isEditing('mission')">
              {{ aboutForm.mission }}
              <el-button class="edit-button" type="primary" link>
                <el-icon>
                  <Edit/>
                </el-icon>
              </el-button>
            </p>
            <!-- 编辑模式 -->
            <el-input v-else
                      v-model="aboutForm.mission"
                      type="textarea"
                      :rows="6"
                      @blur="finishEditing()"
            />
          </div>
        </div>

        <!-- 愿景卡片 -->
        <div class="mission-card">
          <div class="mission-icon">👁️</div>
          <h3 class="mission-title">我们的愿景</h3>
          <div class="editable-wrapper">
            <p class="mission-text" @click="editContent('vision')" v-if="!isEditing('vision')">
              {{ aboutForm.vision }}
              <el-button class="edit-button" type="primary" link>
                <el-icon>
                  <Edit/>
                </el-icon>
              </el-button>
            </p>
            <el-input
                v-else
                v-model="aboutForm.vision"
                type="textarea"
                :rows="4"
                @blur="finishEditing('vision')"
            />
          </div>
        </div>

        <!-- 价值观卡片 -->
        <div class="mission-card">
          <div class="mission-icon">🤝</div>
          <h3 class="mission-title">我们的价值观</h3>
          <div class="editable-wrapper">
            <p class="mission-text" @click="editContent('value')" v-if="!isEditing('value')">
              {{ aboutForm.value }}
              <el-button class="edit-button" type="primary" link>
                <el-icon>
                  <Edit/>
                </el-icon>
              </el-button>
            </p>
            <el-input
                v-else
                v-model="aboutForm.value"
                type="textarea"
                :rows="4"
                @blur="finishEditing('value')"
            />
          </div>
        </div>
      </div>

      <!-- 联系我们部分 -->
      <div class="contact-section">
        <h2 class="section-title">联系我们</h2>
        <div class="contact-content">
          <div class="contact-info">
            <!-- 联系电话 -->
            <div class="contact-item">
              <div class="contact-icon">📞</div>
              <div class="contact-details">
                <h3>联系电话</h3>
                <div @click="editContent('phone')" v-if="!isEditing('phone')">
                  <p>{{ aboutForm.phone }}</p>
                  <el-button class="edit-button" type="primary" link>
                    <el-icon>
                      <Edit/>
                    </el-icon>
                  </el-button>
                </div>
                <el-input v-else v-model="aboutForm.phone" @blur="finishEditing('phone')"/>
              </div>
            </div>

            <!-- 办公地址 -->
            <div class="contact-item">
              <div class="contact-icon">🏢</div>
              <div class="contact-details">
                <h3>办公地址</h3>
                <div @click="editContent('address')" v-if="!isEditing('address')">
                  <p>{{ aboutForm.address }}</p>
                  <el-button class="edit-button" type="primary" link>
                    <el-icon>
                      <Edit/>
                    </el-icon>
                  </el-button>
                </div>
                <el-input v-else v-model="aboutForm.address" @blur="finishEditing('address')"/>
              </div>
            </div>

            <!-- 电子邮箱 -->
            <div class="contact-item">
              <div class="contact-icon">✉️</div>
              <div class="contact-details">
                <h3>电子邮箱</h3>
                <div @click="editContent('email')" v-if="!isEditing('email')">
                  <p>{{ aboutForm.email }}</p>
                  <el-button class="edit-button" type="primary" link>
                    <el-icon>
                      <Edit/>
                    </el-icon>
                  </el-button>
                </div>
                <el-input v-else v-model="aboutForm.email" @blur="finishEditing('email')"/>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 保存按钮区域 -->
      <div class="save-section">
        <el-button type="primary" @click="saveData" class="save-button">
          保存所有内容
        </el-button>
      </div>

    </div>

  </div>
</template>

<script setup>
import {onMounted, ref} from 'vue'
import {addAbout, selectCurrentAbout, selectIsExist, updateAbout} from "@/api/ich/about.js";
import {Edit} from "@element-plus/icons-vue";
import ImageUpload from "@/components/ImageUpload/index.vue";
import {ElMessage} from "element-plus";

//当前正在
// ..编辑的字段名称
const editingField = ref('')

//开始编辑指定字段的内容
const editContent = (field) => {
  editingField.value = field
}

//检查指定字段当前是否在编辑中
const isEditing = (field) => {
  return editingField.value === field
}

//完成编辑指定字段的内容
const finishEditing = () => {
  editingField.value = ''
}

//关于我们表单数据
const aboutForm = ref({})

//创建新的关于我们数据
const createData = () => {
  const items = {}
  addAbout(items).then(res => {
    selectIsExist().then(res => {
      isExist.value = res.data

      selectCurrentAbout().then(res => {
        aboutForm.value = res.data
      })
    })
  })
}

//保存关于我们数据
const saveData = () => {
  updateAbout(aboutForm.value).then(response => {
    selectCurrentAbout().then(res => {
      aboutForm.value = res.data
      ElMessage.success("数据已保存")
    })
  })
}

//关于我们表中有没有数据
const isExist = ref(false)

onMounted(() => {
  selectIsExist().then(res => {
    isExist.value = res.data

    if (isExist.value === true) {
      selectCurrentAbout().then(res => {
        aboutForm.value = res.data
      })
    }
  })
})

</script>

<style scoped>
/*
 * 关于我们页面样式
 * 使用 CSS 变量和现代布局技术
 */

/* 页面容器样式 */
.about-page {
  width: 100%; /* 占据父容器全部宽度 */
  min-height: 100vh; /* 最小高度为视口高度 */
}

/* 按钮容器样式 */
.button-container {
  text-align: center; /* 文本居中对齐 */
  margin-bottom: 20px; /* 底部外边距 */
}

/* 创建按钮样式 */
.create-button {
  font-size: 16px; /* 字体大小 */
  padding: 12px 24px; /* 内边距：上下12px，左右24px */
}

/* 主要内容容器样式 */
.section-container {
  max-width: 1200px; /* 最大宽度限制 */
  margin: 0 auto; /* 水平居中 */
  padding: 20px; /* 内边距 */
}

/* 部分标题通用样式 */
.section-title {
  font-size: 28px; /* 字体大小 */
  font-weight: bold; /* 字体粗细 */
  text-align: center; /* 文本居中对齐 */
  margin-bottom: 40px; /* 底部外边距 */
  color: #333; /* 文字颜色 */
  position: relative; /* 相对定位，为伪元素定位做准备 */
}

/* 部分标题下方的装饰线 */
.section-title::after {
  content: ''; /* 伪元素内容为空 */
  display: block; /* 块级显示 */
  width: 60px; /* 宽度 */
  height: 4px; /* 高度 */
  background: linear-gradient(90deg, #3a7bd5, #00d2ff); /* 渐变背景 */
  margin: 15px auto 0; /* 外边距：上15px，左右自动，下0 */
  border-radius: 2px; /* 圆角 */
}

/* 系统介绍部分样式 */
.intro-section {
  display: flex; /* 弹性布局 */
  align-items: center; /* 垂直居中对齐 */
  gap: 40px; /* 子元素间距 */
  margin-bottom: 60px; /* 底部外边距 */
  background: white; /* 背景色 */
  padding: 40px; /* 内边距 */
  border-radius: 15px; /* 圆角 */
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1); /* 阴影效果 */
  position: relative; /* 相对定位 */
}

/* 介绍内容区域样式 */
.intro-content {
  flex: 1; /* 弹性因子，占据剩余空间 */
}

/* 介绍文本样式 */
.intro-text {
  font-size: 16px; /* 字体大小 */
  line-height: 1.8; /* 行高 */
  color: #666; /* 文字颜色 */
  margin-bottom: 20px; /* 底部外边距 */
  cursor: pointer; /* 鼠标指针样式 */
  position: relative; /* 相对定位 */
  padding-right: 30px; /* 右侧内边距，为编辑按钮留空间 */
}

/* 最后一个介绍文本去掉底部外边距 */
.intro-text:last-child {
  margin-bottom: 0;
}

/* 鼠标悬停时显示编辑按钮 */
.intro-text:hover .edit-button {
  display: inline-block; /* 显示编辑按钮 */
}

/* 介绍图片区域样式 */
.intro-image {
  flex: 1; /* 弹性因子，占据剩余空间 */
  border-radius: 10px; /* 圆角 */
  overflow: hidden; /* 溢出隐藏 */
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15); /* 阴影效果 */
  position: relative; /* 相对定位 */
}

/* 介绍图片样式 */
.intro-image img {
  width: 100%; /* 宽度100% */
  height: auto; /* 高度自适应 */
  display: block; /* 块级显示 */
}

/* 图片编辑覆盖层样式 */
.image-edit-overlay {
  position: absolute; /* 绝对定位 */
  top: 0; /* 顶部对齐 */
  left: 0; /* 左侧对齐 */
  width: 100%; /* 宽度100% */
  height: 100%; /* 高度100% */
  background: rgba(0, 0, 0, 0.6); /* 半透明黑色背景 */
  display: flex; /* 弹性布局 */
  align-items: center; /* 垂直居中对齐 */
  justify-content: center; /* 水平居中对齐 */
  opacity: 0; /* 初始透明度为0（隐藏） */
  transition: opacity 0.3s ease; /* 透明度过渡动画 */
  cursor: pointer; /* 鼠标指针样式 */
  pointer-events: none; /* 禁止鼠标事件 */
}

/* 鼠标悬停时显示覆盖层 */
.image-edit-overlay:hover {
  opacity: 1; /* 完全不透明 */
}

/* 覆盖层内按钮样式 */
.image-edit-overlay :deep(.el-button) {
  pointer-events: auto; /* 允许鼠标事件 */
}

/* 使命愿景部分样式 */
.mission-section {
  display: grid; /* 网格布局 */
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); /* 自适应列数，最小300px */
  gap: 30px; /* 网格间距 */
  margin-bottom: 60px; /* 底部外边距 */
}

/* 使命卡片样式 */
.mission-card {
  background: white; /* 背景色 */
  padding: 30px; /* 内边距 */
  border-radius: 15px; /* 圆角 */
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1); /* 阴影效果 */
  text-align: center; /* 文本居中对齐 */
  transition: all 0.3s ease; /* 所有属性过渡动画 */
  position: relative; /* 相对定位 */
}

/* 使命卡片悬停效果 */
.mission-card:hover {
  transform: translateY(-10px); /* 向上移动10px */
  box-shadow: 0 12px 30px rgba(0, 0, 0, 0.15); /* 增强阴影效果 */
}

/* 使命图标样式 */
.mission-icon {
  font-size: 48px; /* 字体大小 */
  margin-bottom: 20px; /* 底部外边距 */
}

/* 使命标题样式 */
.mission-title {
  font-size: 22px; /* 字体大小 */
  font-weight: bold; /* 字体粗细 */
  margin-bottom: 15px; /* 底部外边距 */
  color: #333; /* 文字颜色 */
}

/* 使命文本样式 */
.mission-text {
  font-size: 16px; /* 字体大小 */
  line-height: 1.7; /* 行高 */
  color: #666; /* 文字颜色 */
  cursor: pointer; /* 鼠标指针样式 */
  padding-right: 30px; /* 右侧内边距，为编辑按钮留空间 */
}

/* 鼠标悬停时显示编辑按钮 */
.mission-text:hover .edit-button {
  display: inline-block; /* 显示编辑按钮 */
}

/* 联系我们部分样式 */
.contact-section {
  margin-bottom: 60px; /* 底部外边距 */
}

/* 联系内容样式 */
.contact-content {
  display: grid; /* 网格布局 */
  grid-template-columns: 1fr 1fr; /* 两列等宽 */
  gap: 40px; /* 网格间距 */
}

/* 联系项目样式 */
.contact-item {
  display: flex; /* 弹性布局 */
  align-items: flex-start; /* 顶部对齐 */
  margin-bottom: 30px; /* 底部外边距 */
}

/* 最后一个联系项目去掉底部外边距 */
.contact-item:last-child {
  margin-bottom: 0;
}

/* 联系图标样式 */
.contact-icon {
  font-size: 28px; /* 字体大小 */
  margin-right: 20px; /* 右侧外边距 */
  flex-shrink: 0; /* 禁止收缩 */
}

/* 联系详情样式 */
.contact-details h3 {
  font-size: 18px; /* 字体大小 */
  font-weight: bold; /* 字体粗细 */
  margin: 0 0 10px 0; /* 外边距 */
  color: #333; /* 文字颜色 */
}

/* 联系详情段落样式 */
.contact-details p {
  font-size: 16px; /* 字体大小 */
  color: #666; /* 文字颜色 */
  margin: 0; /* 外边距清零 */
  cursor: pointer; /* 鼠标指针样式 */
  display: inline-block; /* 行内块级显示 */
}

/* 联系详情容器样式 */
.contact-details div {
  position: relative; /* 相对定位 */
}

/* 鼠标悬停时显示编辑按钮 */
.contact-details div:hover .edit-button {
  display: inline-block; /* 显示编辑按钮 */
}

/* 编辑按钮通用样式 */
.edit-button {
  display: none; /* 默认隐藏 */
  position: absolute; /* 绝对定位 */
  right: 0; /* 右侧对齐 */
  top: 0; /* 顶部对齐 */
}

/* 可编辑包装器样式 */
.editable-wrapper {
  position: relative; /* 相对定位 */
}

/* 鼠标悬停时显示编辑按钮 */
.editable-wrapper:hover .edit-button {
  display: inline-block; /* 显示编辑按钮 */
}

/* 保存按钮区域样式 */
.save-section {
  text-align: center; /* 文本居中对齐 */
  margin-top: 30px; /* 顶部外边距 */
  padding: 20px; /* 内边距 */
}

/* 保存按钮样式 */
.save-button {
  font-size: 16px; /* 字体大小 */
  padding: 12px 30px; /* 内边距 */
  margin-right: 20px; /* 右侧外边距 */

}
</style>
