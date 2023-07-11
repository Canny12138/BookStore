<template>
  <div>
    <!-- <el-container>
  <el-header>This is Book management</el-header>
  <el-container>
    <el-aside width="200px">Aside</el-aside>
    <el-main> -->
    <!--查询界面-->
    <div>
      <el-form
        ref="searchForm"
        :model="searchForm"
        :inline="true"
        class="formItem"
        label-width="80px"
      >
        <el-col :xl="6" :md="8" :sm="24">
          <el-form-item label="书名" prop="title">
            <el-input
              placeholder="请输入书名"
              v-model="searchForm.title"
              clearable
            ></el-input>
          </el-form-item>
        </el-col>

        <el-col :xl="6" :md="8" :sm="24">
          <el-form-item label="状态" prop="useful">
            <el-select
              v-model="searchForm.useful"
              placeholder="请选择状态"
              clearable
            >
              <el-option
                v-for="item in usefulAry"
                :key="item.name"
                :label="item.name"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :xl="6 || 24" :md="8 || 24" :sm="24">
          <el-form-item>
            <div :style="{ float: 'right', overflow: 'hidden' } || {}">
              <el-button icon="el-icon-search" type="primary" @click="search"
                >搜索</el-button
              >
            </div>
          </el-form-item>
        </el-col>
      </el-form>
    </div>
    <div>
      <el-button type="primary" @click="add()">新增</el-button>
      <el-button type="primary" @click="setUsefuls(0)">批量禁用</el-button>
      <el-button type="primary" @click="setUsefuls(1)">批量启用</el-button>
      <el-button type="primary" @click="dels()">批量删除</el-button>
    </div>

    <page-table
      @load-table-data="loadTableData"
      ref="dataTable"
      @selection-change="selection"
      row-key="id"
    >
      <template slot="tb-columns">
        <el-table-column align="center" label="封面">
          <template slot-scope="scope">
            <el-image
              :src="scope.row.imgSrc"
              style="width: 85px; height: 120px"
            />
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          prop="title"
          label="书名"
          :formatter="tableColummEmpty"
        ></el-table-column>
        <el-table-column
          align="center"
          prop="price"
          label="价格"
          :formatter="tableColummEmpty"
        ></el-table-column>
        <el-table-column
          align="center"
          prop="category"
          label="分类"
          :formatter="categoryFormat"
        ></el-table-column>
        <el-table-column
          align="center"
          prop="onSale"
          label="促销"
          :formatter="onSaleFormat"
        >
          <template slot-scope="scope">
            <!-- <el-button
              v-if="scope.row.onSale != 1"
              type="primary"
              @click="sale(scope.row)"
              >设置秒杀</el-button
            > -->
            <el-popover
              v-if="scope.row.onSale != 1"
              placement="top-start"
              title="设置秒杀"
              width="200"
              trigger="click"
            >
              <el-input
                v-model="salePrice"
                placeholder="请输入秒杀价格"
              ></el-input>
              <el-select v-model="saleTimes" placeholder="请选择秒杀时间">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
              <el-button
                type="primary"
                size="mini"
                @click="addSale(scope.row.id)"
                >确定</el-button
              >
              <el-button slot="reference">设置秒杀</el-button>
            </el-popover>
            <div v-else>正在秒杀</div>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          prop="sales"
          label="销量"
          :formatter="tableColummEmpty"
        ></el-table-column>
        <el-table-column
          align="center"
          prop="clicks"
          label="浏览量"
          :formatter="tableColummEmpty"
        ></el-table-column>
        <el-table-column
          align="center"
          prop="useful"
          label="状态"
          :formatter="usefulFormat"
        ></el-table-column>
        <el-table-column
          align="center"
          prop="createTime"
          label="创建时间"
          sortable
        ></el-table-column>

        <el-table-column align="center" label="操作">
          <template slot-scope="scope">
            <el-button type="primary" @click="edit(scope.row)">编辑</el-button>|
            <el-dropdown>
              <el-link :underline="false" type="primary"
                >更多<i class="el-icon-arrow-down el-icon--right"></i>
              </el-link>
              <el-dropdown-menu slot="dropdown">
                <!-- <el-dropdown-item @click="edit(scope.row)">编辑</el-dropdown-item> -->
                <el-dropdown-item
                  @click.native="setUseful(scope.row.id, 1)"
                  v-if="scope.row.useful == 0"
                  >启用</el-dropdown-item
                >
                <el-dropdown-item
                  @click.native="setUseful(scope.row.id, 0)"
                  v-else
                  >停用</el-dropdown-item
                >
                <el-dropdown-item @click.native="del(scope.row.id)"
                  >删除</el-dropdown-item
                >
              </el-dropdown-menu>
            </el-dropdown>

            <!-- <el-button type="primary" @click="setUseful(scope.row.id,0)" v-if="scope.row.useful==1">禁用</el-button>
                <el-button type="primary" @click="setUseful(scope.row.id,1)" v-else>启用</el-button>|  
                <el-button type="primary" @click="del(scope.row.id)">删除</el-button> -->
          </template>
        </el-table-column>
      </template>
    </page-table>

    <!-- </el-main>
  </el-container>
</el-container> -->

    <Edit
      :visible="visible"
      v-if="visible"
      :title="title"
      :defaultFormData="obj"
      @close="close"
    ></Edit>
  </div>
</template>
<script>
import PageTable from "@/components/PageTable";
import Edit from "@/views/edit";

import { page, updateUseful, dele } from "@/api/modules/book";
import { sale_page, sale_add } from "@/api/modules/sale";
export default {
  name: "myBook",
  components: { PageTable, Edit },
  data() {
    return {
      visible: false,
      title: "新增",
      obj: {},
      ids: [],
      searchForm: {
        title: "",
      },
      usefulAry: [],
      options: [
        {
          value: 1,
          label: "2min",
        },
        {
          value: 2,
          label: "20min",
        },
        {
          value: 3,
          label: "2h",
        },
      ],
      salePrice: "",
      saleTimes: "",
    };
  },
  mounted() {
    this.usefulAry = this.$store.state.dicMap[this.gl_useful];
  },
  methods: {
    usefulFormat(row, column, cellValue) {
      return this.columnFormat(cellValue, this.usefulAry);
    },
    categoryFormat(row, column, cellValue) {
      if (cellValue == 1) {
        return "文学";
      }
      if (cellValue == 2) {
        return "科技";
      }
      if (cellValue == 3) {
        return "教育";
      }
      if (cellValue == 4) {
        return "历史";
      }
      if (cellValue == 5) {
        return "艺术";
      }
    },
    onSaleFormat(row, column, cellValue) {
      if (cellValue == 1) {
        return "正在秒杀";
      } else {
        return "无";
      }
    },
    addSale(bookId) {
      if (this.salePrice == "" || this.saleTimes == "") {
        this.toast("请输入正确");
        return;
      }
      let myTimes = 1;
      if (this.saleTimes == 1) {
        myTimes = 6;
      }
      if (this.saleTimes == 2) {
        myTimes = 15;
      }
      if (this.saleTimes == 3) {
        myTimes = 18;
      }
      let self = this;
      sale_add({
        bookId: bookId,
        price: self.salePrice,
        times: myTimes,
      }).then((res) => {
        self.salePrice = "";
        self.saleTimes = "";
      });
      this.toast("设置成功！");
      this.$refs.dataTable.loadTableData("reload");
    },
    dels() {
      if (this.ids.length <= 0) {
        this.toast("请先选则记录", 2);
        return;
      }

      this.myDel(this.ids.join(","));
    },
    del(id) {
      this.myDel(id);
    },

    myDel(ids) {
      this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          dele({
            ids: ids,
          })
            .then((res) => {
              this.toast(res.message);
              this.$refs.dataTable.loadTableData("reload");
            })
            .catch((err) => {
              this.toast(err, 2);
            });
        })
        .catch(() => {});
    },
    setUsefuls(useful) {
      if (this.ids.length <= 0) {
        this.toast("请先选则记录", 2);
        return;
      }

      this.myUpdateUseful(this.ids.join(","), useful);
    },
    setUseful(id, useful) {
      this.myUpdateUseful(id, useful);
    },

    myUpdateUseful(ids, useful) {
      updateUseful({
        ids: ids,
        useful: useful,
      })
        .then((res) => {
          this.toast(res.message);
          this.$refs.dataTable.loadTableData("reload");
        })
        .catch((err) => {
          this.toast(err, 2);
        });
    },
    edit(obj) {
      console.log(obj);
      this.visible = true;
      this.title = "编辑";
      this.obj = obj;
    },
    add() {
      this.visible = true;
      this.title = "新增";
      this.obj = {};
    },
    close(flag) {
      this.visible = false;
      if (flag) {
        this.$refs.dataTable.loadTableData("reload");
      }
    },
    selection(val) {
      this.ids = [];
      val.forEach((item) => {
        this.ids.push(item.id);
      });
      // console.log(this.ids)
    },
    search() {
      this.$refs.dataTable.loadTableData("reload");
    },
    loadTableData(filter, callback) {
      filter = {
        ...filter,
        ...this.searchForm,
      };
      //console.log(filter)

      page(filter).then((res) => {
        //res = result
        // console.log(res)

        this.total = res.data.total;
        let tableData = {
          ...res.data,
          data: res.data.records,
        };
        callback(tableData);
      });
    },
  },
};
</script>
<style scoped>
.el-header,
.el-footer {
  background-color: #b3c0d1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #d3dce6;
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
  line-height: 160px;
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}

.formItem {
  vertical-align: middle;
}
</style>