import {post,postJson} from '../axios'

export function page(params){
   return post('/soft/order/pageAll',params)
}

// export function add(params){
//    return postJson('/soft/user/save',params)
// }

export function update(params){
   return post('/soft/order/updateStatus',params)
}