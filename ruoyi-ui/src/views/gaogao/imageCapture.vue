<template>
  <div style="text-align: center;border: 1px dashed #c0ccda;border-radius: 6px;">
    <div class="user-info-head" @click="editCropper()">
      <img v-if="imageShow" v-bind:src="options.img" title="点击截取图片" class="img-lg"  alt=""/>
      <i style="font-size: 28px;color: #8c939d;" v-else class="el-icon-plus"></i>
    </div>
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body @opened="modalOpened"  @close="closeDialog">
      <el-row>
        <el-col :xs="24" :md="12" :style="{height: '350px'}">
          <vue-cropper
            ref="cropper"
            :img="options.img"
            :info="true"
            :autoCrop="options.autoCrop"
            :autoCropWidth="options.autoCropWidth"
            :autoCropHeight="options.autoCropHeight"
            :fixedBox="options.fixedBox"
            :outputType="options.outputType"
            :full="true"
            :high="true"
            @realTime="realTime"
            v-if="visible"
          />
        </el-col>
        <el-col :xs="24" :md="12" :style="{height: '350px'}">
          <div class="avatar-upload-preview">
            <img :src="previews.url" :style="previews.img" />
          </div>
        </el-col>
      </el-row>
      <br />
      <el-row>
        <el-col :lg="2" :sm="3" :xs="3">
          <el-upload action="#" :http-request="requestUpload" :show-file-list="false" :before-upload="beforeUpload">
            <el-button size="small">
              选择
              <i class="el-icon-upload el-icon--right"></i>
            </el-button>
          </el-upload>
        </el-col>
        <el-col :lg="{span: 1, offset: 2}" :sm="2" :xs="2">
          <el-button icon="el-icon-plus" size="small" @click="changeScale(1)"></el-button>
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :sm="2" :xs="2">
          <el-button icon="el-icon-minus" size="small" @click="changeScale(-1)"></el-button>
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :sm="2" :xs="2">
          <el-button icon="el-icon-refresh-left" size="small" @click="rotateLeft()"></el-button>
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :sm="2" :xs="2">
          <el-button icon="el-icon-refresh-right" size="small" @click="rotateRight()"></el-button>
        </el-col>
        <el-col :lg="{span: 2, offset: 6}" :sm="2" :xs="2">
          <el-button type="primary" size="small" @click="uploadImg()">提 交</el-button>
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
import {VueCropper} from "vue-cropper";
import {imageCapture} from "@/api/system/user";
import {debounce} from '@/utils'
import heic2any from 'heic2any';

export default {
  components: { VueCropper },
  props: { filePath: String },
  data() {
    return {
      // 是否显示弹出层
      open: false,
      // 是否显示cropper
      visible: false,
      // 弹出层标题
      title: "截取图片",
      options: {
        img: null,  //裁剪图片的地址
        autoCrop: true,             // 是否默认生成截图框
        autoCropWidth: 358,         // 默认生成截图框宽度
        autoCropHeight: 208,        // 默认生成截图框高度
        fixedBox: true,             // 固定截图框大小 不允许改变
        outputType:"png",           // 默认生成截图为PNG格式
        filename: 'avatar'          // 文件名称
      },
      previews: {},
      resizeHandler: null,
      baseUrl: process.env.VUE_APP_BASE_API,
      imageShow: false
    };
  },
  created() {
    this.options.img = this.baseUrl + this.filePath
    this.imageShow = this.filePath
  },
  methods: {
    // 编辑头像
    editCropper() {
      this.open = true;
    },
    // 打开弹出层结束时的回调
    modalOpened() {
      this.visible = true;
      if (!this.resizeHandler) {
        this.resizeHandler = debounce(() => {
          this.refresh()
        }, 100)
      }
      window.addEventListener("resize", this.resizeHandler)
    },
    // 刷新组件
    refresh() {
      this.$refs.cropper.refresh();
    },
    // 覆盖默认的上传行为
    requestUpload() {
    },
    // 向左旋转
    rotateLeft() {
      this.$refs.cropper.rotateLeft();
    },
    // 向右旋转
    rotateRight() {
      this.$refs.cropper.rotateRight();
    },
    // 图片缩放
    changeScale(num) {
      num = num || 1;
      this.$refs.cropper.changeScale(num);
    },

    // 上传预处理
    async beforeUpload(file) {
      if (file.type.indexOf("image/") === -1) {
        if(file.name.indexOf(".HEIC")===-1){
          this.$modal.msgError("文件格式错误，请上传图片类型,如：JPG，PNG后缀的文件。");
        }else{
          const blob = await heic2any({ blob: file, toType: 'image/jpeg' });
          const jpgFile = new File([blob], `${file.name.split('.')[0] || 'image'}.jpg`, { type: 'image/jpeg' });
          const reader = new FileReader();
          reader.readAsDataURL(jpgFile);
          reader.onload = () => {
            this.options.img = reader.result;
            this.options.filename = jpgFile.name;
          };
        }

      } else {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
          this.options.img = reader.result;
          this.options.filename = file.name;
        };
      }

    },

    // 上传图片
    uploadImg() {

      this.$refs.cropper.getCropBlob(data => {
        let formData = new FormData();
        formData.append("avatarfile", data, this.options.filename);
        imageCapture(formData).then(response => {
          this.open = false;
          this.$emit('changeValue', response.imgUrl)
          this.$modal.msgSuccess("修改成功");
          this.imageShow = true
          this.visible = false;
        });
      });

    },
    // 实时预览
    realTime(data) {
      this.previews = data;
    },
    // 关闭窗口
    closeDialog() {
      this.options.img = this.baseUrl + this.filePath
      this.visible = false;
      window.removeEventListener("resize", this.resizeHandler)
    }
  }
};
</script>
<style scoped lang="scss">
.user-info-head {
  position: relative;
  display: inline-block;
  height: 120px;
  line-height: 120px;
  width: -webkit-fill-available;
}

.avatar-upload-preview{
  position: relative;
  top: 50%;
  left: 50%;
  //-webkit-transform: translate(-50%, -50%);
  //transform: translate(-50%, -50%);
  width: 358px;
  height: 208px;
  border-radius: 0;
  -webkit-box-shadow: 0 0 4px #ccc;
  box-shadow: 0 0 4px #ccc;
  overflow: hidden;
}
.img-lg{
  width: 358px;
  height: 208px;
}
</style>
