<template>
  <div>
    <el-page-header
      @back="goBack"
      content="收藏夹"
      style="margin-top: 20px; margin-left: 10px"
    ></el-page-header>
    <el-empty v-if="total == 0" description="收藏夹空空如也"></el-empty>
    <el-main style="margin: 10px">
      <el-row>
        <div v-if="!loading" style="margin-left: 100px; margin-right: 100px">
          <div
            v-for="(o, index) in bookTable.length"
            :key="o"
            :offset="index > 0 ? 2 : 0"
            v-loading="loading"
            style="width: 212px; margin: 10px; float: left"
          >
            <span v-if="bookTable[o - 1]">
              <el-card
                v-loading="bookTable[o - 1].useful == '0'"
                element-loading-text="已售空"
                element-loading-spinner="none"
                :body-style="{ padding: '0px' }"
              >
                <el-image
                  :src="bookTable[o - 1].imgSrc"
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
                  <span v-if="bookTable[o - 1]" style="font-size: 110%">
                    {{ bookTable[o - 1].title }}
                  </span>
                  <div class="bottom clearfix">
                    <span v-if="bookTable[o - 1]">
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
                        {{ Math.floor(bookTable[o - 1].price) }}
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
                              bookTable[o - 1].price -
                              Math.floor(bookTable[o - 1].price)
                            ).toFixed(2) * 100
                          }}
                        </span>
                      </span>
                    </span>
                    <el-popconfirm
                      @confirm="delCollect(o - 1)"
                      title="确认取消收藏？"
                    >
                      <el-button
                        slot="reference"
                        type="warning"
                        icon="el-icon-star-off"
                        circle
                        title="取消收藏"
                        style="position: relative; top: -4px; left: 20px"
                      ></el-button>
                    </el-popconfirm>
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
</style>

<script>
import { getcollect, addcollect, delcollect } from "@/api/modules/user_collect";
import { getStore } from "@/libs/storage";
import { page } from "@/api/modules/book";
export default {
  data() {
    return {
      pageTable: [],
      bookTable: [],
      //   bookid: "",
      searchForm: {
        pageNum: 1,
        pageSize: 100,
        userId: getStore("userid"),
      },
      collectForm: {
        pageNum: 1,
        pageSize: 100,
        userId: "",
        bookId: "",
      },
      bookForm: {
        pageNum: 1,
        pageSize: 100,
        bookid: "",
      },
      loading: true,
      currentPage: 1,
      total: 0,
      dialogVisible: false,
    };
  },
  methods: {
    getbook() {
      let self = this;
      let i = 0;
      this.searchForm.pageNum = this.currentPage;
      this.searchForm.pageSize = 100;
      self.loading = true;
      getcollect(this.searchForm).then((res) => {
        self.pageTable = res.data.records;
        self.total = res.data.total;
        console.log(res.data);
        self.loading = false;

        console.log(self.total);
        for (; i < self.total; i++) {
          self.bookForm.bookid = self.pageTable[i].bookId;
          page(self.bookForm).then((res) => {
            self.bookTable.push(res.data.records[0]);
          });
        }
      });
    },

    delCollect(i) {
      this.collectForm.userId = getStore("userid");
      this.collectForm.bookId = this.bookTable[i].id;
      delcollect(this.collectForm)
        .then((res) => {
          if (res.success) {
            this.toast("取消收藏");
          } else {
            this.toast(res.messsage, "error");
          }
          this.bookTable.splice(i, 1);
          this.total--;
          this.$forceUpdate();
          console.log("dataUpdated");
        })
        .catch((err) => {
          this.toast(err, 2);
        });
    },
    goBack() {
      this.navigate("MarketHome");
    },
  },

  mounted() {
    this.getbook();
    // console.log(getStore("userid"));
    // console.log(this.bookTable);
  },
};
</script>