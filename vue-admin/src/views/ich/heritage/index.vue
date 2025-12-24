<template>
  <div class="app-container">
    <!-- 顶部搜索 -->
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="项目名称" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入项目名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类别" prop="category">
        <el-input
          v-model="queryParams.category"
          placeholder="请输入类别"
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
      <el-col :span="1.5">
        <el-button
            type="primary"
            icon="Upload"
            size="mini"
            @click="handleImport"
        >导入</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 表格 -->
    <el-table @row-click="clickRow" ref="table" highlight-current-row
              border v-loading="loading" :data="heritageList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" :index="indexMethod" />
      <el-table-column label="非遗项目ID" align="center" prop="heritageId" />
      <el-table-column label="项目名称" align="center" prop="title" />
      <el-table-column label="项目简介" align="center" prop="description" />
      <el-table-column label="详细介绍" align="center" prop="detailDescription" />
      <el-table-column label="图片" align="center" prop="image" width="100">
        <template #default="scope">
          <image-preview :src="scope.row.image" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="所在地" align="center" prop="location" />
      <el-table-column label="类别" align="center" prop="category" />
      <el-table-column label="传承状态" align="center" prop="status" />
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

    <!-- 导入对话框 -->
    <vxe-modal :title="upload.title" v-model="upload.open" width="400px" showFooter show-zoom resize>
      <el-upload
          ref="uploadRef"
          :limit="1"
          accept=".xlsx, .xls"
          :headers="upload.headers"
          :action="upload.url"
          :data="{ updateSupport: upload.updateSupport }"
          :disabled="upload.isUploading"
          :on-progress="handleFileUploadProgress"
          :on-success="handleFileSuccess"
          :auto-upload="false"
          drag
      >
        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <template #tip>
            <div class="el-upload__tip text-center">
              <span>仅允许导入xls、xlsx格式文件。</span>
              <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
            </div>
        </template>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </vxe-modal>

    <!-- 添加或修改非遗项目对话框 -->
    <vxe-modal :title="title" v-model="open" width="500px" show-maximize showFooter resize>
      <el-form ref="heritageRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="项目名称" prop="title">
          <el-input v-model="form.title" placeholder="请输入项目名称" />
        </el-form-item>
        <el-form-item label="项目简介" prop="description">
          <el-input v-model="form.description" placeholder="请输入项目简介" />
        </el-form-item>
        <el-form-item label="详细介绍">
          <editor v-model="form.detailDescription" :min-height="192"/>
        </el-form-item>
        <el-form-item label="图片" prop="image">
          <image-upload v-model="form.image"/>
        </el-form-item>
        <el-form-item label="所在地" prop="location">
          <el-input v-model="form.location" placeholder="请输入所在地" />
        </el-form-item>
        <el-form-item label="类别" prop="category">
          <el-input v-model="form.category" placeholder="请输入类别" />
        </el-form-item>
        <el-form-item label="传承状态" prop="status">
          <el-input v-model="form.status" type="textarea" placeholder="请输入内容" />
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

<script setup name="Heritage">
import { listHeritage, getHeritage, delHeritage, addHeritage, updateHeritage } from "@/api/ich/heritage"
import {getToken} from "@/utils/auth.js";
const baseURL = import.meta.env.VITE_APP_BASE_API

const { proxy } = getCurrentInstance()

const heritageList = ref([])
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
    title: null,
    category: null,
    status: null,
  },
  rules: {
    title: [
      { required: true, message: "项目名称不能为空", trigger: "blur" }
    ],
    description: [
      { required: true, message: "项目简介不能为空", trigger: "blur" }
    ],
    detailDescription: [
      { required: true, message: "详细介绍不能为空", trigger: "blur" }
    ],
    image: [
      { required: true, message: "图片不能为空", trigger: "blur" }
    ],
    location: [
      { required: true, message: "所在地不能为空", trigger: "blur" }
    ],
    category: [
      { required: true, message: "类别不能为空", trigger: "blur" }
    ],
    status: [
      { required: true, message: "传承状态不能为空", trigger: "blur" }
    ],
  },
  // 导入参数
  upload: {
    // 是否显示弹出层（导入）
    open: false,
    // 弹出层标题（导入）
    title: "",
    // 是否禁用上传
    isUploading: false,
    // 设置上传的请求头部
    headers: { Authorization: "Bearer " + getToken() },
    // 上传的地址
    url: baseURL + "/ich/heritage/importData"
  }
})

const { queryParams, form, rules, upload } = toRefs(data)

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

/** 查询非遗项目列表 */
const getList = () => {
  loading.value = true
  listHeritage(queryParams.value).then(response => {
    heritageList.value = response.rows
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
    heritageId: null,
    title: null,
    description: null,
    detailDescription: null,
    image: null,
    location: null,
    category: null,
    status: null,
    createTime: null
  }
  proxy.resetForm("heritageRef")
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
  ids.value = selection.map(item => item.heritageId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset()
  open.value = true
  title.value = "添加非遗项目"
}

/** 修改按钮操作 */
const handleUpdate = (row) => {
  reset()
  const _heritageId = row.heritageId || ids.value
  getHeritage(_heritageId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改非遗项目"
  })
}

/** 提交按钮 */
const submitForm = () => {
  proxy.$refs["heritageRef"].validate(valid => {
    if (valid) {
      if (form.value.heritageId != null) {
        updateHeritage(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addHeritage(form.value).then(response => {
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
  const _heritageIds = row.heritageId || ids.value
  proxy.$modal.confirm('是否确认删除该项数据？').then(function() {
    return delHeritage(_heritageIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy.download('ich/heritage/export', {
    ...queryParams.value
  }, `heritage_${new Date().getTime()}.xlsx`)
}

/** 下载模板操作 */
const importTemplate = () => {
  proxy.download('ich/heritage/importTemplate', {
  }, `template_${new Date().getTime()}.xlsx`)
}

/** 导入按钮操作 */
const handleImport = () => {
  upload.value.title = "非遗项目导入";
  upload.value.open = true;
}

// 文件上传中处理
const handleFileUploadProgress = (event, file, fileList) => {
  upload.value.isUploading = true;
}

// 文件上传成功处理
const handleFileSuccess = (response, file, fileList) => {
  upload.value.open = false;
  upload.value.isUploading = false;
  proxy.$refs.uploadRef.clearFiles();
  proxy.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
  getList();
}

// 提交上传文件
const submitFileForm = () => {
  proxy.$refs.uploadRef.submit();
}

getList()
</script>
