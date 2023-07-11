import {post,postJson,del} from '../axios'

export function page(params){
   return post('/soft/book/page',params)
}

export function add(params){
   return postJson('/soft/book/save',params)
}

export function updateUseful(params){
   return post('/soft/book/updateUseful',params)
}

export function dele(params){
   return del('/soft/book/delete',params)
}