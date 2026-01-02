<template>
  <div class="app-container">
    <!-- 顶部搜索 -->
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="姓名" prop="name">
        <el-input
            v-model="queryParams.name"
            placeholder="请输入姓名"
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

      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 表格 -->
    <el-table @row-click="clickRow" ref="table" highlight-current-row
              border v-loading="loading" :data="masterList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="序号" align="center" type="index" :index="indexMethod"/>
      <el-table-column label="姓名" align="center" prop="name"/>
      <el-table-column label="技艺专长" align="center" prop="skills"/>
      <el-table-column label="位置" align="center" prop="location"/>
      <el-table-column label="照片" align="center" prop="image" width="100">
        <template #default="scope">
          <image-preview :src="scope.row.image" :width="50" :height="50"/>
        </template>
      </el-table-column>
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



    <!-- 添加或修改传承人对话框 -->
    <vxe-modal :title="title" v-model="open" width="50%" show-maximize showFooter resize>
      <el-card>
        <el-form ref="masterRef" :model="form" :rules="rules" label-width="80px">
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="姓名" prop="name">
                <el-input v-model="form.name" placeholder="请输入姓名"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="技艺专长" prop="skills">
                <el-input v-model="form.skills" placeholder="请输入技艺专长"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="位置" prop="location">
                <el-input type="textarea" rows="7" v-model="form.location" placeholder="请输入位置"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="照片" prop="image">
                <image-upload v-model="form.image"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-divider content-position="center">传承贡献信息</el-divider>
          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button type="primary" icon="Plus" @click="handleAddMasterContribution">添加</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button type="danger" icon="Delete" @click="handleDeleteMasterContribution">删除</el-button>
            </el-col>
          </el-row>
          <el-table height="40vh" :data="masterContributionList" :row-class-name="rowMasterContributionIndex"
                    @selection-change="handleMasterContributionSelectionChange" ref="masterContribution">
            <el-table-column type="selection" width="50" align="center"/>
            <el-table-column label="序号" align="center" prop="index" width="50"/>
            <el-table-column label="贡献内容" prop="content">
              <template #default="scope">
                <el-input v-model="scope.row.content" placeholder="请输入贡献内容"/>
              </template>
            </el-table-column>
          </el-table>
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

<script setup name="Master">
import {listMaster, getMaster, delMaster, addMaster, updateMaster} from "@/api/ich/master"
import {getToken} from "@/utils/auth.js";

const baseURL = import.meta.env.VITE_APP_BASE_API

const {proxy} = getCurrentInstance()

const masterList = ref([])
const masterContributionList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const checkedMasterContribution = ref([])
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
  },
  rules: {
    name: [
      {required: true, message: "姓名不能为空", trigger: "blur"}
    ],
    skills: [
      {required: true, message: "技艺专长不能为空", trigger: "blur"}
    ],
    location: [
      {required: true, message: "位置不能为空", trigger: "blur"}
    ],
    image: [
      {required: true, message: "照片不能为空", trigger: "blur"}
    ]
  },

})

const {queryParams, form, rules} = toRefs(data)

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

/** 查询传承人列表 */
const getList = () => {
  loading.value = true
  listMaster(queryParams.value).then(response => {
    masterList.value = response.rows
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
    masterId: null,
    name: null,
    skills: null,
    location: null,
    image: null
  }
  masterContributionList.value = []
  proxy.resetForm("masterRef")
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
  ids.value = selection.map(item => item.masterId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset()
  open.value = true
  title.value = "添加传承人"
}

/** 修改按钮操作 */
const handleUpdate = (row) => {
  reset()
  const _masterId = row.masterId || ids.value
  getMaster(_masterId).then(response => {
    form.value = response.data
    masterContributionList.value = response.data.masterContributionList
    open.value = true
    title.value = "修改传承人"
  })
}

/** 提交按钮 */
const submitForm = () => {
  proxy.$refs["masterRef"].validate(valid => {
    if (valid) {
      form.value.masterContributionList = masterContributionList.value
      if (form.value.masterId != null) {
        updateMaster(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addMaster(form.value).then(response => {
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
  const _masterIds = row.masterId || ids.value
  proxy.$modal.confirm('是否确认删除该项数据？').then(function () {
    return delMaster(_masterIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {
  })
}

/** 传承贡献序号 */
const rowMasterContributionIndex = ({row, rowIndex}) => {
  row.index = rowIndex + 1
}

/** 传承贡献添加按钮操作 */
const handleAddMasterContribution = () => {
  let obj = {}
  obj.content = ""
  masterContributionList.value.push(obj)
}

/** 传承贡献删除按钮操作 */
const handleDeleteMasterContribution = () => {
  if (checkedMasterContribution.value.length == 0) {
    proxy.$modal.msgError("请先选择要删除的传承贡献数据")
  } else {
    const masterContributions = masterContributionList.value
    const checkedMasterContributions = checkedMasterContribution.value
    masterContributionList.value = masterContributions.filter(function (item) {
      return checkedMasterContributions.indexOf(item.index) == -1
    })
  }
}

/** 复选框选中数据 */
const handleMasterContributionSelectionChange = (selection) => {
  checkedMasterContribution.value = selection.map(item => item.index)
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy.download('ich/master/export', {
    ...queryParams.value
  }, `master_${new Date().getTime()}.xlsx`)
}


getList()
</script>
