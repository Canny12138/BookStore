import {post,postJson,del} from '../axios'

export function getcollect(params){
   return post('/soft/user-collect/page',params)
}

export function addcollect(params){
   return post('/soft/user-collect/save',params)
}

export function updateUseful(params){
   return post('/soft/user-collect/updateUseful',params)
}

export function delcollect(params){
   return del('/soft/user-collect/delete',params)
}