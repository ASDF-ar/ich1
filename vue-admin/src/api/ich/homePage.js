import request from '@/utils/request'

// 查询非遗项目数 传承人数 新闻资讯数 用户数
export function selectHomeCount() {
    return request({
        url: '/home/page/selectHomeCount',
        method: 'get'
    })
}

// 查询首页柱状图数据
export function selectBarData() {
    return request({
        url: '/home/page/selectBarData',
        method: 'get'
    })
}

// 查询项目分类统计饼图数据
export function selectPieData() {
    return request({
        url: '/home/page/selectPieData',
        method: 'get'
    })
}