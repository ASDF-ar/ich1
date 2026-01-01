<template>
    <div style="border: 1px solid #ccc; z-index: 1005;">
        <!-- 只在非只读模式下显示工具栏 -->
        <Toolbar
                v-if="!props.readOnly"
                style="border-bottom: 1px solid #ccc"
                :editor="editorRef"
                :defaultConfig="toolbarConfig"
                :mode="mode"
        />
        <Editor
                style="overflow-y: hidden;"
                :style="{ height: editorHeight + 'px' }"
                v-model="content"
                :defaultConfig="editorConfig"
                :mode="mode"
                @onCreated="handleCreated"
                @onChange="handleChange"
                @onFocus="handleFocus"
                @onBlur="handleBlur"
        />
    </div>
</template>

<script setup>
import { onBeforeUnmount, ref, shallowRef, onMounted, computed } from 'vue'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import '@wangeditor/editor/dist/css/style.css'
import { getToken } from "@/utils/auth"
import axios from 'axios'

// 获取wangEditor的创建器
import { createEditor, createToolbar, Boot } from '@wangeditor/editor'
import markdownModule from '@wangeditor/plugin-md'

// 注册markdown模块
Boot.registerModule(markdownModule)

const { proxy } = getCurrentInstance()

// API 地址
const uploadUrl = ref(import.meta.env.VITE_APP_BASE_API + "/common/upload")
const headers = ref({
    Authorization: "Bearer " + getToken()
})

const props = defineProps({
    /* 编辑器的内容 */
    modelValue: {
        type: String,
        default: ''
    },
    /* 高度 */
    height: {
        type: Number,
        default: 300
    },
    /* 最小高度 */
    minHeight: {
        type: Number,
        default: null,
    },
    /* 只读 */
    readOnly: {
        type: Boolean,
        default: false,
    },
    /* 上传文件大小限制(MB) */
    fileSize: {
        type: Number,
        default: 5,
    },
    /* 类型（base64格式、url格式） */
    type: {
        type: String,
        default: "url",
    }
})

const emit = defineEmits(['update:modelValue'])

// 编辑器实例，必须用 shallowRef
const editorRef = shallowRef()

const mode = 'default' // 或 'simple'

// 内容
const content = ref('')

// 计算编辑器高度（减去工具栏高度）
const editorHeight = computed(() => {
    return props.height - 45 // 45px是工具栏的大致高度
})

// 监听内容变化
watch(() => props.modelValue, (val) => {
    if (val !== content.value) {
        content.value = val || ''
    }
}, { immediate: true })

// 工具栏配置
const toolbarConfig = {}

// 编辑器配置
const editorConfig = ref({
    placeholder: '请输入内容...',
    readOnly: props.readOnly,
    autoFocus: false,
    scroll: true,
    MENU_CONF: {},
    hoverbarKeys: {
        // 自定义悬浮菜单
        text: {
            menuKeys: ['bold', 'italic', 'through', 'underline', 'code', 'sub', 'sup', 'color', 'bgColor', '|',
                'fontSize', 'fontFamily', 'justify', 'indent', '|', 'clearStyle']
        },
        link: {
            menuKeys: ['editLink', 'unLink', 'viewLink']
        }
    }
})

// 启用Markdown快捷键支持
editorConfig.value.MENU_CONF['markdown'] = {
    enable: true
}

// 代码块配置
editorConfig.value.MENU_CONF['codeBlock'] = {
    language: 'javascript',  // 默认语言
    theme: 'default'
}

// 图片上传配置
editorConfig.value.MENU_CONF['uploadImage'] = {
    server: uploadUrl.value,
    fieldName: 'file',
    maxFileSize: props.fileSize * 1024 * 1024, // 转换为字节
    headers: headers.value,
    onError(file, err, res) {
        console.error('图片上传出错', err, res)
        proxy.$modal.msgError("图片上传失败")
    },
    onSuccess(res) {
        console.log('图片上传成功', res)
    }
}

// 自定义上传图片
if (props.type == 'url') {
    editorConfig.value.MENU_CONF['uploadImage'] = {
        async customUpload(file, insertFn) {
            // JS 自定义上传
            const formData = new FormData()
            formData.append("file", file)

            // 检查文件类型
            const allowedTypes = ["image/jpeg", "image/jpg", "image/png", "image/gif", "image/svg+xml"]
            if (!allowedTypes.includes(file.type)) {
                proxy.$modal.msgError(`图片格式错误!`)
                return
            }

            // 检查文件大小
            if (props.fileSize && file.size / 1024 / 1024 > props.fileSize) {
                proxy.$modal.msgError(`上传文件大小不能超过 ${props.fileSize} MB!`)
                return
            }

            try {
                const response = await axios.post(uploadUrl.value, formData, {
                    headers: {
                        "Content-Type": "multipart/form-data",
                        "Authorization": headers.value.Authorization
                    }
                })

                if (response.data.code === 200) {
                    // 插入图片
                    insertFn(import.meta.env.VITE_APP_BASE_API + response.data.fileName, '', '')
                } else {
                    proxy.$modal.msgError("图片插入失败")
                }
            } catch (err) {
                proxy.$modal.msgError("图片上传失败")
                console.error('上传失败', err)
            }
        }
    }
}

// 粘贴图片处理
const handlePaste = (event) => {
    if (props.type !== 'url') return

    const editor = editorRef.value
    if (!editor) return

    // 获取粘贴数据
    const clipboardData = event.clipboardData || window.clipboardData
    if (!clipboardData) return

    // 检查是否有文件数据
    const items = clipboardData.items
    if (items) {
        for (let i = 0; i < items.length; i++) {
            const item = items[i]
            // 检查是否为图片文件
            if (item.type.indexOf('image') !== -1) {
                event.preventDefault()
                const file = item.getAsFile()
                if (file) {
                    // 通过编辑器插入图片
                    editor.command.execute('insertImage', {
                        src: '', // 先插入空的src
                        alt: file.name,
                        href: ''
                    })
                }
                return
            }
        }
    }

    // 处理HTML内容粘贴
    const html = clipboardData.getData('text/html')
    if (html) {
        // WangEditor会自动处理HTML内容
        return
    }
}

// 上传图片的函数
const uploadImage = (file) => {
    return new Promise((resolve, reject) => {
        // 检查文件类型
        const allowedTypes = ["image/jpeg", "image/jpg", "image/png", "image/gif", "image/svg+xml"]
        if (!allowedTypes.includes(file.type)) {
            proxy.$modal.msgError(`图片格式错误!`)
            return reject(new Error('图片格式错误'))
        }

        // 检查文件大小
        if (props.fileSize && file.size / 1024 / 1024 > props.fileSize) {
            proxy.$modal.msgError(`上传文件大小不能超过 ${props.fileSize} MB!`)
            return reject(new Error('文件大小超过限制'))
        }

        const formData = new FormData()
        formData.append("file", file)

        axios.post(uploadUrl.value, formData, {
            headers: {
                "Content-Type": "multipart/form-data",
                "Authorization": headers.value.Authorization
            }
        }).then(response => {
            if (response.data.code === 200) {
                resolve(import.meta.env.VITE_APP_BASE_API + response.data.fileName)
            } else {
                proxy.$modal.msgError("图片插入失败")
                reject(new Error('图片插入失败'))
            }
        }).catch(err => {
            proxy.$modal.msgError("图片上传失败")
            reject(err)
        })
    })
}


// 处理键盘快捷键
const handleKeyDown = (event) => {
    // 检查是否是 Ctrl + Alt + 数字键 (1-6)
    if (event.ctrlKey && event.altKey && !event.shiftKey) {
        const editor = editorRef.value
        if (!editor) return

        // 数字键 1-6 对应标题级别 1-6
        if (event.keyCode >= 49 && event.keyCode <= 54) { // 49是'1'的keyCode, 54是'6'的keyCode
            event.preventDefault()

            // 获取标题级别 (1-6)
            const level = event.keyCode - 48

            // 使用正确的方法设置标题
            editor.insertNode({ type: 'header', level, children: [{ text: '' }] })
            editor.focus()
        }
    }
}

onMounted(() => {
    // 监听粘贴事件
    document.addEventListener('paste', handlePaste)
    // 监听键盘事件
    document.addEventListener('keydown', handleKeyDown)
})

// 组件销毁时也要销毁编辑器
onBeforeUnmount(() => {
    const editor = editorRef.value
    if (editor == null) return
    editor.destroy()
    document.removeEventListener('paste', handlePaste)
    document.removeEventListener('keydown', handleKeyDown)
})

const handleCreated = (editor) => {
    console.log('editor created', editor)
    editorRef.value = editor // 记录 editor 实例，重要！
}

const handleChange = (editor) => {
    emit('update:modelValue', editor.getHtml())
}

const handleFocus = (editor) => {
    console.log('focus', editor)
}

const handleBlur = (editor) => {
    console.log('blur', editor)
}
</script>

<style>
.editor-img-uploader {
    display: none;
}

.w-e-toolbar {
    z-index: 1005 !important;
}

.w-e-text-container [data-slate-editor] p {
    margin: 0;
}

.editor, .ql-toolbar {
    white-space: pre-wrap !important;
    line-height: normal !important;
}

/* 代码块样式优化 */
pre.code-content {
    background-color: #f5f5f5;
    padding: 10px;
    border-radius: 4px;
    font-family: monospace;
}

/* 代码块工具栏样式 */
.w-e-bar-item button[name="codeBlock"]::after {
    content: "代码块";
}

/* 快捷键提示样式 */
.w-e-text-container [data-slate-editor] p.hint {
    color: #999;
    font-size: 12px;
}
</style>
