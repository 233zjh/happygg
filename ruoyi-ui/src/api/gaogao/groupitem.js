import request from '@/utils/request'

// 查询分组信息列表
export function listGroupitem(query) {
  return request({
    url: '/gaogao/groupitem/list',
    method: 'get',
    params: query
  })
}

// 查询分组信息详细
export function getGroupitem(id) {
  return request({
    url: '/gaogao/groupitem/' + id,
    method: 'get'
  })
}

// 新增分组信息
export function addGroupitem(data) {
  return request({
    url: '/gaogao/groupitem',
    method: 'post',
    data: data
  })
}

// 修改分组信息
export function updateGroupitem(data) {
  return request({
    url: '/gaogao/groupitem',
    method: 'put',
    data: data
  })
}

// 删除分组信息
export function delGroupitem(id) {
  return request({
    url: '/gaogao/groupitem/' + id,
    method: 'delete'
  })
}

export function hideGroupItem(id, status) {
  return request({
    url: '/gaogao/groupitem/hidden',
    method: 'post',
    data: {id: id, isHidden: status}
  })
}

export function listGroupItemAll(query) {
  return request({
    url: '/gaogao/groupitem/listAll',
    method: 'get',
    params: query
  })
}
