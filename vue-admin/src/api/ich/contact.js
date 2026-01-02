import request from '@/utils/request'

// 查询留言反馈列表
export function listContact(query) {
  return request({
    url: '/ich/contact/list',
    method: 'get',
    params: query
  })
}

// 查询留言反馈详细
export function getContact(contactId) {
  return request({
    url: '/ich/contact/' + contactId,
    method: 'get'
  })
}

// 新增留言反馈
export function addContact(data) {
  return request({
    url: '/ich/contact',
    method: 'post',
    data: data
  })
}

// 修改留言反馈
export function updateContact(data) {
  return request({
    url: '/ich/contact',
    method: 'put',
    data: data
  })
}

// 删除留言反馈
export function delContact(contactId) {
  return request({
    url: '/ich/contact/' + contactId,
    method: 'delete'
  })
}
