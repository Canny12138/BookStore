import {post,postJson,del} from '../axios'

export function sale_page(params){
   return post('/soft/sale/page',params)
}

export function sale_add(params){
   return post('/soft/sale/save',params)
}

// export function updateUseful(params){
//    return post('/soft/book-sale/updateUseful',params)
// }

// export function dele(params){
//    return del('/soft/book-sale/delete',params)
// }