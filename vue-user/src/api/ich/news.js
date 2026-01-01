import request from '@/utils/request'

// 查询新闻资讯列表
export function listNews(query) {
  return request({
    url: '/ich/news/list',
    method: 'get',
    params: query
  })
}

// 查询新闻资讯详细
export function getNews(newsId) {
  return request({
    url: '/ich/news/' + newsId,
    method: 'get'
  })
}

// 新增新闻资讯
export function addNews(data) {
  return request({
    url: '/ich/news',
    method: 'post',
    data: data
  })
}

// 修改新闻资讯
export function updateNews(data) {
  return request({
    url: '/ich/news',
    method: 'put',
    data: data
  })
}

// 删除新闻资讯
export function delNews(newsId) {
  return request({
    url: '/ich/news/' + newsId,
    method: 'delete'
  })
}


// 查询焦点新闻信息
export function selectFocusNews() {
  return request({
    url: '/ich/news/selectFocusNews',
    method: 'get'
  })
}

// 查询非焦点新闻列表, 并根据发布时间倒序排序
export function selectNonFocusNewsList() {
  return request({
    url: '/ich/news/selectNonFocusNewsList',
    method: 'get'
  })
}