<template>
  <vxe-modal title="传承人详情" v-model="open" width="50%" show-maximize showFooter resize>
    <el-descriptions
        :column="2"
        border
    >
      <el-descriptions-item>
        <template #label>
          姓名
        </template>
        {{ master.name }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          技艺专长
        </template>
        {{ master.skills }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          位置
        </template>
        {{ master.location }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          照片
        </template>
        <image-preview :src="master.image" :width="50" :height="50"/>
      </el-descriptions-item>
    </el-descriptions>
    <br>
    <el-table height="40vh" :data="master.masterContributionList">
      <el-table-column label="序号" align="center" type="index" width="50"/>
      <el-table-column label="贡献内容" prop="content"/>
    </el-table>
  </vxe-modal>
</template>

<script setup>
import {ref, defineExpose} from 'vue'
import {getMaster} from "@/api/ich/master.js";

const open = ref(false)

//传承人数据
const master = ref({})

//父组件调用方法
const handleOpen = (masterId) => {
  getMaster(masterId).then(res => {
    master.value = res.data
    open.value = true
  })
}

//暴露方法
defineExpose({
  handleOpen
})
</script>

<style scoped>

</style>