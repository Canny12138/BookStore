<template>
  <el-dialog
    :title="title"
    :visible="visible"
    :close-on-click-modal="false"
    :before-close="handleClose"
    width="500px"
  >
    <el-form :model="formData" :rules="rules" ref="formData" label-width="80px">
      <el-form-item label="书名" prop="title">
        <el-input v-model="formData.title"></el-input>
      </el-form-item>
      <el-form-item label="价格" prop="price">
        <el-input v-model="formData.price"></el-input>
      </el-form-item>
      <el-form-item label="库存" prop="stock">
        <el-input v-model="formData.stock"></el-input>
      </el-form-item>
      <el-form-item label="简介" prop="introduction">
        <el-input v-model="formData.introduction"></el-input>
      </el-form-item>
      <el-form-item label="分类" prop="category">
        <el-input v-model="formData.category"></el-input>
      </el-form-item>
      <p>1-'文学', 2-'科技', 3-'教育', 4-'历史', 5-'艺术'</p>
      <el-form-item label="封面" prop="cover">
        <img
          :src="formData.imgSrc"
          class="image"
          style="width: 210px; height: 250px"
        />
        <el-upload
          class="upload-demo"
          action="/api/save"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :file-list="fileList"
          :on-success="handle_success"
          list-type="picture"
          style="margin-top: 30px; margin-left: 0px"
        >
          <el-button size="small" type="primary">点击上传</el-button>
          <div slot="tip" class="el-upload__tip">
            只能上传jpg/png文件，且不超过100MB
          </div>
        </el-upload>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button type="text" @click="handleClose">取 消</el-button>
      <el-button type="primary" @click="handleSubmit" :loading="loading">{{
        loading ? "提交中..." : "确认提交"
      }}</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { page, add } from "@/api/modules/book";
export default {
  name: "edit",
  props: {
    visible: {
      type: Boolean,
    },
    title: {
      type: String,
      default: "新增",
    },
    defaultFormData: {
      type: Object,
    },
  },
  data() {
    return {
      loading: false,
      formData: {
        title: "",
        price: "",
        imgSrc: "",
        stock: 0,
        introduction: "",
        category: 1,

        onSale: 0,
        clicks: 0,
        sales: 0,
        collects: 0,
      },
      refresh: false,
      rules: {
        title: [
          { required: true, message: "请填写书名", trigger: "blur" },
        ],
        price: [{ required: true, message: "请填写价格", trigger: "blur" }],
      },
      fileList: [],
    };
  },
  mounted() {
    if (JSON.stringify(this.defaultFormData) == "{}") {
    } else {
      //console.log('before:'+this.formData)
      this.formData = JSON.parse(JSON.stringify(this.defaultFormData));
      // console.log('after:'+this.formData)
    }
  },
  methods: {
    handleClose() {
      this.$emit("close", this.refresh);
    },
    handleSubmit() {
      this.$refs.formData.validate((valid) => {
        if (valid) {
          add({
            ...this.formData,
          })
            .then((res) => {
              this.refresh = true;
              this.handleClose();
            })
            .catch((err) => {
              this.toast(err, 2);
            });
        }
      });
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    handle_success(res) {
      console.log(res);
      this.$message.success("图片上传成功");
      this.formData.imgSrc = res.data;
      this.$forceUpdate();
    },
  },
};
</script>

<style scoped>
</style>
