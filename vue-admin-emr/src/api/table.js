import request from '@/utils/request'

const baseUrl = 'http://localhost:9528'

export function getList(params) {
  return request({
    url: `${baseUrl}/vue-admin-template/table/list`,
    method: 'get',
    params
  })
}
