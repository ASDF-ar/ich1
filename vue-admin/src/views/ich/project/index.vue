<template>
  <div class="app-container">
    <!-- 顶部搜索 -->
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="项目名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入项目名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目申报类别ID" prop="categoryId">
        <el-input
          v-model="queryParams.categoryId"
          placeholder="请输入项目申报类别ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申报人用户名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入申报人用户ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 顶部按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
        >导出</el-button>
      </el-col>

      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 表格 -->
    <el-table @row-click="clickRow" ref="table" highlight-current-row
              border v-loading="loading" :data="projectList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" :index="indexMethod" />
      <el-table-column label="项目名称" align="center" prop="name" />
      <el-table-column label="项目申报类别ID" align="center" prop="categoryId" />
      <el-table-column label="项目简介" align="center" prop="introduction" />
      <el-table-column label="详细描述" align="center" prop="description" />
      <el-table-column label="传承谱系" align="center" prop="inheritance" />
      <el-table-column label="传承人信息" align="center" prop="inheritors" />
      <el-table-column label="联系方式" align="center" prop="contact" />
      <el-table-column label="申报人用户名" align="center" prop="userName" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />



    <!-- 添加或修改项目申报对话框 -->
    <vxe-modal :title="title" v-model="open" width="500px" show-maximize showFooter resize>
      <el-form ref="projectRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="项目名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入项目名称" />
        </el-form-item>
        <el-form-item label="项目申报类别ID" prop="categoryId">
          <el-input v-model="form.categoryId" placeholder="请输入项目申报类别ID" />
        </el-form-item>
        <el-form-item label="项目简介" prop="introduction">
          <el-input v-model="form.introduction" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="详细描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="传承谱系" prop="inheritance">
          <el-input v-model="form.inheritance" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="传承人信息" prop="inheritors">
          <el-input v-model="form.inheritors" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="联系方式" prop="contact">
          <el-input v-model="form.contact" placeholder="请输入联系方式" />
        </el-form-item>

      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </vxe-modal>
  </div>
</template>

<script setup name="Project">
import { listProject, getProject, delProject, addProject, updateProject } from "@/api/ich/project"
import {getToken} from "@/utils/auth.js";
const baseURL = import.meta.env.VITE_APP_BASE_API

const { proxy } = getCurrentInstance()

const projectList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const selectedRow = ref(null)

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    name: null,
    categoryId: null,
    userId: null,
    userName: null,
  },
  rules: {
    name: [
      { required: true, message: "项目名称不能为空", trigger: "blur" }
    ],
    categoryId: [
      { required: true, message: "项目申报类别ID不能为空", trigger: "blur" }
    ],
    introduction: [
      { required: true, message: "项目简介不能为空", trigger: "blur" }
    ],
    description: [
      { required: true, message: "详细描述不能为空", trigger: "blur" }
    ],
    inheritance: [
      { required: true, message: "传承谱系不能为空", trigger: "blur" }
    ],
    inheritors: [
      { required: true, message: "传承人信息不能为空", trigger: "blur" }
    ],
    contact: [
      { required: true, message: "联系方式不能为空", trigger: "blur" }
    ],
    userId: [
      { required: true, message: "申报人用户ID不能为空", trigger: "blur" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

//点击行 获取行
const clickRow = (row) => {
  selectedRow.value = row; // 更新选中的行
  const table = proxy.$refs.table;
  // 清除所有已选中的行
  table.clearSelection();
  // 选中当前点击的行
  table.toggleRowSelection(row, true);
}

/** 自定义序号 */
const indexMethod = (index) => {
  let pageNum = queryParams.value.pageNum - 1;
  if((pageNum!==-1 && pageNum!==0)){
    return (index + 1) + (pageNum  * queryParams.value.pageSize);
  }else{
    return (index + 1)
  }
}

/** 查询项目申报列表 */
const getList = () => {
  loading.value = true
  listProject(queryParams.value).then(response => {
    projectList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

// 取消按钮
const cancel = () => {
  open.value = false
  reset()
}

// 表单重置
const reset = () => {
  form.value = {
    projectId: null,
    name: null,
    categoryId: null,
    introduction: null,
    description: null,
    inheritance: null,
    inheritors: null,
    contact: null,
    userId: null,
    createTime: null
  }
  proxy.resetForm("projectRef")
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  proxy.resetForm("queryRef")
  handleQuery()
}

// 多选框选中数据
const handleSelectionChange = (selection) => {
  ids.value = selection.map(item => item.projectId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset()
  open.value = true
  title.value = "添加项目申报"
}

/** 修改按钮操作 */
const handleUpdate = (row) => {
  reset()
  const _projectId = row.projectId || ids.value
  getProject(_projectId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改项目申报"
  })
}

/** 提交按钮 */
const submitForm = () => {
  proxy.$refs["projectRef"].validate(valid => {
    if (valid) {
      if (form.value.projectId != null) {
        updateProject(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addProject(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

/** 删除按钮操作 */
const handleDelete = (row) => {
  const _projectIds = row.projectId || ids.value
  proxy.$modal.confirm('是否确认删除该项数据？').then(function() {
    return delProject(_projectIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy.download('ich/project/export', {
    ...queryParams.value
  }, `project_${new Date().getTime()}.xlsx`)
}


getList()
</script>
