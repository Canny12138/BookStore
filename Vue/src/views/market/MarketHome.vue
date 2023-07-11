<template>
  <div>
    <el-main style="margin: 10px">
      <el-row>
        <el-col :span="4"><p></p></el-col>
        <el-col :span="16">
          <el-carousel :interval="4000" type="card" height="300px">
            <el-carousel-item
              v-for="item in saleTotal"
              :key="item"
              :style="{ backgroundImage: 'url(${saleTable[item - 1].imgSrc})' }"
            >
              <h3 class="medium">
                <span v-if="saleTable[item - 1]">{{
                  saleTable[item - 1].title
                }}</span>
              </h3>
              <el-image
                src="img/sale.png"
                class="image"
                style="
                  position: relative;
                  top: -200px;
                  left: 330px;
                  width: 300px;
                  height: 200px;
                  z-index: 1;
                "
              />
              <!-- <el-image
                src="img/sale_back.pnga"
                class="image"
                style="
                  position: relative;
                  top: -477px;
                  left: 356px;
                  width: 290px;
                  height: 550px;
                "
              /> -->
              <el-image
                :src="saleTable[item - 1].imgSrc"
                class="image"
                style="position: relative; top: -400px; width: 400px"
              />
              <!-- width: 203px;
                  height: 300px; -->
            </el-carousel-item>
          </el-carousel>
        </el-col>
        <el-col :span="4"><p></p></el-col>
      </el-row>
      <!-- <button @click="updatea"></button> -->

      <el-row>
        <div v-if="!loading" style="margin-left: 100px; margin-right: 100px">
          <div
            v-for="(o, index) in pageTable.length"
            :key="o"
            :offset="index > 0 ? 2 : 0"
            v-loading="loading"
            style="width: 212px; margin: 10px; float: left"
          >
            <span v-if="pageTable[o - 1]">
              <el-card
                v-loading="pageTable[o - 1].useful == '0'"
                element-loading-text="已售空"
                element-loading-spinner="none"
                :body-style="{ padding: '0px' }"
              >
                <el-image
                  :src="pageTable[o - 1].imgSrc"
                  fit="cover"
                  class="image"
                  style="width: 210px; height: 250px"
                >
                  <div
                    slot="placeholder"
                    class="image-slot"
                    style="position: relative; top: 140px"
                  >
                    <i class="el-icon-picture-outline"></i>
                  </div>
                </el-image>
                <div style="padding: 14px">
                  <span v-if="pageTable[o - 1]" style="font-size: 110%">
                    {{ pageTable[o - 1].title }}
                  </span>
                  <div class="bottom clearfix">
                    <span v-if="pageTable[o - 1]">
                      <span
                        style="
                          font-size: 150%;
                          font-weight: bold;
                          font-family: 'Hiragino Sans GB';
                        "
                      >
                        <span
                          style="
                            font-size: 75%;
                            position: relative;
                            top: -4px;
                            left: 4px;
                          "
                        >
                          ¥
                        </span>
                        {{ Math.floor(pageTable[o - 1].price) }}
                        <span
                          style="
                            font-size: 75%;
                            position: relative;
                            top: -4px;
                            left: -6px;
                          "
                        >
                          .{{
                            (
                              pageTable[o - 1].price -
                              Math.floor(pageTable[o - 1].price)
                            ).toFixed(2) * 100
                          }}
                        </span>
                      </span>
                    </span>
                    <!-- <time class="time">{{ currentDate }}</time> -->
                    <!-- <el-button type="text" class="button">操作按钮</el-button> -->
                    <el-button
                      v-if="!pageTable[o - 1].collected"
                      @click="addCollect(o - 1)"
                      icon="el-icon-star-off"
                      circle
                      title="收藏"
                      style="position: relative; top: -4px; left: 20px"
                    ></el-button>
                    <el-button
                      v-else
                      @click="delCollect(o - 1)"
                      type="warning"
                      icon="el-icon-star-off"
                      circle
                      title="取消收藏"
                      style="position: relative; top: -4px; left: 20px"
                    ></el-button>
                  </div>
                </div>
              </el-card>
            </span>
          </div>
        </div>
      </el-row>
      <el-row>
        <div class="block" @click="getbook">
          <el-pagination
            layout="prev, pager, next"
            :total="(total / 14) * 10"
            :current-page.sync="currentPage"
          >
          </el-pagination>
        </div>
      </el-row>
    </el-main>
  </div>
</template>

<style>
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}

.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}

.el-loading-spinner .el-loading-text {
  color: #909399;
  font-size: large;
}

.el-select .el-input {
  width: 100px;
}
.input-with-select .el-input-group__prepend {
  background-color: #fff;
}
</style>

<script>
import { page, updateUseful, dele } from "@/api/modules/book";
import { getcollect, addcollect, delcollect } from "@/api/modules/user_collect";
import { sale_page } from "@/api/modules/book_sale";
import { getStore } from "@/libs/storage";
export default {
  data() {
    return {
      // imgSrc: "img/1.jpg",
      pageTable: [],
      // imgSrc: []
      searchForm: {
        pageNum: 1,
        pageSize: 14,
      },
      collectForm: {
        pageNum: 1,
        pageSize: 100,
        userId: "",
        bookId: "",
      },
      saleTable: [],
      loading: true,
      currentPage: 1,
      total: 0,
      saleTotal: 0,
      saleSrc: "img/sale.png",
    };
  },
  methods: {
    getbook() {
      let self = this;
      this.searchForm.pageNum = this.currentPage;
      this.searchForm.pageSize = 14;
      self.loading = true;
      page(this.searchForm).then((res) => {
        self.pageTable = res.data.records;
        self.total = res.data.total;
        for (let i = 0; i < res.data.records.length; i++) {
          self.isCollected(i);
        }
        // console.log(self.pageTable);
        // console.log(res.data);
        self.loading = false;
      });
    },
    updatea() {
      this.$forceUpdate();
      console.log("aaaaaaaaa");
    },
    getsale() {
      let self = this;
      this.searchForm.pageNum = 1;
      this.searchForm.pageSize = 100;
      // self.loading = true;
      sale_page(this.searchForm).then((res) => {
        self.saleTable = res.data.records;
        self.saleTotal = res.data.total;
        // console.log(res.data);
        // self.loading = false;
      });
    },

    addCollect(i) {
      this.collectForm.userId = getStore("userid");
      this.collectForm.bookId = this.pageTable[i].id;
      addcollect(this.collectForm)
        .then((res) => {
          if (res.success) {
            this.toast("收藏成功");
          } else {
            this.toast(res.messsage, "error");
          }
          this.$set(this.pageTable[i], "collected", true);
        })
        .catch((err) => {
          this.toast(err, 2);
        });
    },

    delCollect(i) {
      this.collectForm.userId = getStore("userid");
      this.collectForm.bookId = this.pageTable[i].id;
      delcollect(this.collectForm)
        .then((res) => {
          if (res.success) {
            this.toast("取消收藏");
          } else {
            this.toast(res.messsage, "error");
          }
          this.$set(this.pageTable[i], "collected", false);
        })
        .catch((err) => {
          this.toast(err, 2);
        });
    },

    isCollected(i) {
      this.collectForm.userId = getStore("userid");
      this.collectForm.bookId = this.pageTable[i].id;
      getcollect(this.collectForm).then((res) => {
        if (res.data.total > 0) {
          // this.pageTable[i].collected = true;
          this.$set(this.pageTable[i], "collected", true);
          // console.log(this.pageTable[i].collected);
        } else {
          // this.pageTable[i].collected = false;
          this.$set(this.pageTable[i], "collected", false);
          // console.log(this.pageTable[i].collected);
        }
      });
    },
  },
  mounted() {
    this.getbook();
    this.getsale();
  },
};
</script>