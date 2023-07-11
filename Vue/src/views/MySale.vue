<template>
  <div>
    <!--查询界面-->
    <!-- <div>
      <el-form
        ref="searchForm"
        :model="searchForm"
        :inline="true"
        class="formItem"
        label-width="80px"
      >
        <el-col :xl="6" :md="8" :sm="24">
          <el-form-item label="订单号" prop="username">
            <el-input
              placeholder="请输入订单号"
              v-model="searchForm.username"
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
    </div> -->
    <div>
      <el-button type="primary" @click="add()" v-has="this.gl_btn_add"
        >新增</el-button
      >
      <el-button
        type="primary"
        @click="setUsefuls(0)"
        v-has="this.gl_btn_useful"
        >批量禁用</el-button
      >
      <el-button
        type="primary"
        @click="setUsefuls(1)"
        v-has="this.gl_btn_useful"
        >批量启用</el-button
      >
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
              :src="scope.row.book.imgSrc"
              style="width: 85px; height: 120px"
            />
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          prop="book.title"
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
          prop="times"
          label="秒杀时间"
          :formatter="timesFormat"
        ></el-table-column>
        <el-table-column
          align="center"
          prop="times"
          label="剩余时间"
          :formatter="leftTimeFormat"
        ></el-table-column>
        <el-table-column
          align="center"
          prop="createTime"
          label="创建时间"
          sortable
        ></el-table-column>

        <!-- <el-table-column align="center" label="操作" prop="status">
          <template slot-scope="scope">
            <el-button
              type="primary"
              v-if="scope.row.status == 2"
              @click="edit(scope.row)"
              >发货</el-button
            >
          </template>
        </el-table-column> -->
      </template>
    </page-table>

    <Edit
      :visible="visible"
      v-if="visible"
      :title="title"
      :defaultFormData="obj"
      @close="close"
    ></Edit>
    <user-role
      :visible="visible_u_r"
      v-if="visible_u_r"
      :user="user"
      @close="close"
    ></user-role>
  </div>
</template>
    <script>
import PageTable from "@/components/PageTable";
import Edit from "./urm/user/edit";
import UserRole from "./urm/user/user-role";

import { sale_page } from "@/api/modules/sale";
export default {
  name: "mySale",
  components: { PageTable, Edit, UserRole },
  data() {
    return {
      visible: false,
      visible_u_r: false,
      title: "新增",
      obj: {},
      ids: [],
      user: {},
      searchForm: {
        //   username: "",
        //   useful: "",
      },
      usefulAry: [],
    };
  },
  mounted() {
    this.usefulAry = this.$store.state.dicMap[this.gl_useful];
  },
  methods: {
    setRole(user) {
      this.user = user;
      this.visible_u_r = true;
    },
    timesFormat(row, column, cellValue) {
      if (cellValue == 6) {
        return "2分钟"; //2min
      }
      if (cellValue == 15) {
        return "20分钟"; //20min
      }
      if (cellValue == 18) {
        return "2小时"; //2h
      }
    },
    leftTimeFormat(row, column, cellValue) {
      let addTime = 0;
      if (cellValue == 6) {
        addTime = 2 * 60 * 1000; //2min
      }
      if (cellValue == 15) {
        addTime = 20 * 60 * 1000; //20min
      }
      if (cellValue == 18) {
        addTime = 120 * 60 * 1000; //2h
      }
      let leftTime = row.createTimeId - new Date().getTime() + addTime;
      // console.log(new Date().getTime().toString())
      let hour = Math.floor(leftTime / 3600000);
      let minute = Math.floor((leftTime % 3600000) / 60000);
      let second = Math.floor(((leftTime % 3600000) % 60000) / 1000);
      // return this.columnFormat(cellValue, this.usefulAry);
      return hour + "时" + minute + "分" + second + "秒";
    },

    setUsefuls(useful) {
      if (this.ids.length <= 0) {
        this.toast("请先选择记录", 2);
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
      this.obj = obj;
      update({
        orderId: this.obj.id,
        status: 3,
      }).then((res) => {
        console.log(res);
        this.$refs.dataTable.loadTableData("reload");
      });
    },
    add() {
      this.visible = true;
      this.title = "新增";
      this.obj = {};
    },
    close(flag) {
      this.visible = false;
      this.visible_u_r = false;
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

      sale_page(filter).then((res) => {
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