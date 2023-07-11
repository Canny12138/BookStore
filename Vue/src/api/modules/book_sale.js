import {post,postJson,del} from '../axios'

export function sale_page(params){
   return post('/soft/book-sale/page',params)
}

export function add(params){
   return postJson('/soft/book-sale/save',params)
}

export function updateUseful(params){
   return post('/soft/book-sale/updateUseful',params)
}

export function dele(params){
   return del('/soft/book-sale/delete',params)
}