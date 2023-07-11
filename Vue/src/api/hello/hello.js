import {get,post,postJson} from '../axios'

//hello的get请求，xxxxx
export function myGet(params){
   return get('/test/get',params)
}

export function myPost(params){
    return post('/test/postTest',params)
 }

 export function myPostJson(params){
    return postJson('/test/postJson',params)
 }