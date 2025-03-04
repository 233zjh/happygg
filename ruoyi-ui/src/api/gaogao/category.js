import request from '@/utils/request'

// 查询分类列表
export function listCategory(query) {
  return request({
    url: '/gaogao/category/list',
    method: 'get',
    params: query
  })
}

// 查询分类详细
export function getCategory(id) {
  return request({
    url: '/gaogao/category/' + id,
    method: 'get'
  })
}

// 新增分类
export function addCategory(data) {
  return request({
    url: '/gaogao/category',
    method: 'post',
    data: data
  })
}

// 修改分类
export function updateCategory(data) {
  return request({
    url: '/gaogao/category',
    method: 'put',
    data: data
  })
}

// 删除分类
export function delCategory(id) {
  return request({
    url: '/gaogao/category/' + id,
    method: 'delete'
  })
}

// 分类显隐
export function hideCategory(id, status) {
  return request({
    url: '/gaogao/category/hidden',
    method: 'post',
    data: {id: id, isHidden: status}
  })
}

export function listCategoryAll(query) {
  return request({
    url: '/gaogao/category/listAll',
    method: 'get',
    params: query
  })
}
