<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分组" prop="groupItemId">
        <el-select v-model="queryParams.groupItemId" placeholder="请选择分组">
          <el-option v-for="item in groupitemList" :key="item.id" :label="item.groupItemName" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="是否隐藏" prop="isHidden">
        <el-radio-group v-model="queryParams.isHidden">
          <el-radio-button v-for="dict in dict.type.yes_no" :label="dict.value">{{ dict.label }}</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleBatchAdd"
          v-hasPermi="['gaogao:item:add']"
        >批量新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['gaogao:item:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['gaogao:item:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['gaogao:item:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['gaogao:item:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="itemList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="名称" align="center" prop="name" />
      <el-table-column label="类型" align="center" prop="type" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.category_type" :value="scope.row.type==null?1:scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="分组" align="center" prop="groupItemName" />
      <el-table-column label="文件路径" align="center" prop="filePath" >
        <template slot-scope="scope">
          <image-preview v-if="scope.row.type===1||scope.row.type==null" :src="scope.row.filePath" :width="50" :height="50"/>
          <video-preview v-else :src="scope.row.filePath" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="描述" align="center" prop="description" />
      <el-table-column label="是否隐藏" align="center" prop="isHidden">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.yes_no" :value="scope.row.isHidden==null?0:scope.row.isHidden"/>
        </template>
      </el-table-column>
      <el-table-column label="排序" align="center" prop="sort" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleHidden(scope.row)"
            v-hasPermi="['gaogao:item:edit']"
          >{{ scope.row.isHidden ? '显示' : '隐藏' }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['gaogao:item:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['gaogao:item:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改图片或视频对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="名称" prop="name" v-if="!batch">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-radio-group v-model="form.type">
            <el-radio-button v-for="dict in dict.type.category_type" :label="dict.value">{{ dict.label }}</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="分组" prop="groupItemId">
          <el-select v-model="form.groupItemId" placeholder="请选择分组">
            <el-option v-for="item in groupitemList" :key="item.id" :label="item.groupItemName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="文件路径" prop="filePath">
          <file-upload :limit="batch?8:1" :file-type="form.type === 1||form.type === null ? ['png', 'jpg', 'jpeg', 'HEIC']:['mp4']" :file-size="form.type === 1||form.type === null ? 10:100" v-model="form.filePath"/>
        </el-form-item>
        <el-form-item label="描述" prop="description" v-if="!batch">
          <el-input v-model="form.description" placeholder="请输入描述" />
        </el-form-item>
        <el-form-item label="排序" prop="sort" v-if="!batch">
          <el-input v-model="form.sort" placeholder="请输入排序" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listItem, getItem, delItem, addItem, updateItem, hideItem } from "@/api/gaogao/item";
import {hideGroupItem, listGroupItemAll} from "@/api/gaogao/groupitem";

export default {
  name: "Item",
  dicts: ['yes_no', 'category_type'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 图片或视频表格数据
      itemList: [],
      groupitemList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        type: null,
        groupItemId: null,
        isHidden: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        filePath: [
          {
            required: true,
            message: '请上传文件',
            trigger: 'blur'
          }
        ],
        groupItemId: [
          {
            required: true,
            message: '请选择分组',
            trigger: 'blur'
          }
        ],
      },
      // 是否批量新增
      batch: false
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询图片或视频列表 */
    getList() {
      this.loading = true;
      listItem(this.queryParams).then(response => {
        this.itemList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
      listGroupItemAll(this.queryParams).then(response => {
        this.groupitemList = response.data;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        name: null,
        type: null,
        groupItemId: null,
        filePath: null,
        description: null,
        isHidden: null,
        sort: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.form.type = 1;
      this.batch = false;
      this.title = "添加图片或视频";
    },
    handleBatchAdd(){
      this.reset();
      this.open = true;
      this.form.type = 1;
      this.batch = true;
      this.title = "批量添加图片或视频";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getItem(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改图片或视频";
      });
    },
    handleHidden(row){
      const name = row.name
      this.$modal.confirm('是否隐藏名称为"' + name + '"的数据项？').then(function() {
        return hideItem(row.id, row.isHidden ? 0 : 1);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("操作成功");
      }).catch(() => {});
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateItem(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addItem(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除图片或视频编号为"' + ids + '"的数据项？').then(function() {
        return delItem(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('gaogao/item/export', {
        ...this.queryParams
      }, `item_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
