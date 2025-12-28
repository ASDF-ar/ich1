<template>
  <div class="app-container">
    <!-- 顶部搜索 -->
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="标题" prop="title">
        <el-input
            v-model="queryParams.title"
            placeholder="请输入标题"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发布单位" prop="author">
        <el-input
            v-model="queryParams.author"
            placeholder="请输入发布单位"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类型" prop="category">
        <el-select style="width: 200px" v-model="queryParams.category" placeholder="请选择类型" clearable>
          <el-option
              v-for="dict in news_category"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
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
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
        >导出
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="primary"
            icon="Upload"
            size="mini"
            @click="handleImport"
        >导入
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 表格 -->
    <el-table @row-click="clickRow" ref="table" highlight-current-row
              border v-loading="loading" :data="newsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="序号" align="center" type="index" :index="indexMethod"/>
      <el-table-column label="标题" align="center" prop="title"/>
      <el-table-column label="摘要" align="center" prop="summary"/>
      <el-table-column label="内容" align="center" prop="content" show-overflow-tooltip/>
      <el-table-column label="封面" align="center" prop="image" width="100">
        <template #default="scope">
          <image-preview :src="scope.row.image" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="发布单位" align="center" prop="author"/>
      <el-table-column label="类型" align="center" prop="category">
        <template #default="scope">
          <dict-tag :options="news_category" :value="scope.row.category"/>
        </template>
      </el-table-column>
      <el-table-column label="发布时间" align="center" prop="createTime"/>


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
        <el-icon class="el-icon--upload">
          <upload-filled/>
        </el-icon>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <template #tip>
          <div class="el-upload__tip text-center">
            <span>仅允许导入xls、xlsx格式文件。</span>
            <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;"
                     @click="importTemplate">下载模板
            </el-link>
          </div>
        </template>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </vxe-modal>

    <!-- 添加或修改新闻资讯对话框 -->
    <vxe-modal :title="title" v-model="open" width="50%" show-maximize showFooter resize>
      <el-card>
      <el-form ref="newsRef" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="标题" prop="title">
              <el-input v-model="form.title" placeholder="请输入标题"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">

            <el-form-item label="发布单位" prop="author">
              <el-input v-model="form.author" placeholder="请输入发布单位"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">

            <el-form-item label="类型" prop="category">
              <el-select v-model="form.category" placeholder="请选择类型">
                <el-option
                    v-for="dict in news_category"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="摘要" prop="summary">
              <el-input :rows="7" v-model="form.summary" type="textarea" placeholder="请输入内容"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="封面" prop="image">
              <image-upload v-model="form.image"/>
            </el-form-item>

          </el-col>
        </el-row>

          <el-form-item label="内容">
            <editor v-model="form.content" :min-height="192"/>
          </el-form-item>



      </el-form>
      </el-card>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </vxe-modal>
  </div>
</template>

<script setup name="News">
import {listNews, getNews, delNews, addNews, updateNews} from "@/api/ich/news"
import {getToken} from "@/utils/auth.js";

const baseURL = import.meta.env.VITE_APP_BASE_API

const {proxy} = getCurrentInstance()
const {news_category} = proxy.useDict('news_category')

const newsList = ref([])
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
    author: null,
    category: null,
  },
  rules: {
    title: [
      {required: true, message: "标题不能为空", trigger: "blur"}
    ],
    summary: [
      {required: true, message: "摘要不能为空", trigger: "blur"}
    ],
    content: [
      {required: true, message: "内容不能为空", trigger: "blur"}
    ],
    image: [
      {required: true, message: "封面不能为空", trigger: "blur"}
    ],
    author: [
      {required: true, message: "发布单位不能为空", trigger: "blur"}
    ],
    category: [
      {required: true, message: "类型不能为空", trigger: "change"}
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
    headers: {Authorization: "Bearer " + getToken()},
    // 上传的地址
    url: baseURL + "/ich/news/importData"
  }
})

const {queryParams, form, rules, upload} = toRefs(data)

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
  if ((pageNum !== -1 && pageNum !== 0)) {
    return (index + 1) + (pageNum * queryParams.value.pageSize);
  } else {
    return (index + 1)
  }
}

/** 查询新闻资讯列表 */
const getList = () => {
  loading.value = true
  listNews(queryParams.value).then(response => {
    newsList.value = response.rows
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
    newsId: null,
    title: null,
    summary: null,
    content: null,
    image: null,
    author: null,
    category: null,
    createTime: null
  }
  proxy.resetForm("newsRef")
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
  ids.value = selection.map(item => item.newsId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset()
  open.value = true
  title.value = "添加新闻资讯"
}

/** 修改按钮操作 */
const handleUpdate = (row) => {
  reset()
  const _newsId = row.newsId || ids.value
  getNews(_newsId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改新闻资讯"
  })
}

/** 提交按钮 */
const submitForm = () => {
  proxy.$refs["newsRef"].validate(valid => {
    if (valid) {
      if (form.value.newsId != null) {
        updateNews(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addNews(form.value).then(response => {
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
  const _newsIds = row.newsId || ids.value
  proxy.$modal.confirm('是否确认删除该项数据？').then(function () {
    return delNews(_newsIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {
  })
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy.download('ich/news/export', {
    ...queryParams.value
  }, `news_${new Date().getTime()}.xlsx`)
}

/** 下载模板操作 */
const importTemplate = () => {
  proxy.download('ich/news/importTemplate', {}, `template_${new Date().getTime()}.xlsx`)
}

/** 导入按钮操作 */
const handleImport = () => {
  upload.value.title = "新闻资讯导入";
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
  proxy.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", {dangerouslyUseHTMLString: true});
  getList();
}

// 提交上传文件
const submitFileForm = () => {
  proxy.$refs.uploadRef.submit();
}

getList()
</script>
