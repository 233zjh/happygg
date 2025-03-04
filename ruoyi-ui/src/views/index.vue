<template>
  <div class="box" id="box">
    <div class="box-imgs" ref="boxImgs1">
      <img v-for="big in bigImageList" @load="handleImageLoad" :src="realSrc(big.filePath)" alt="">
    </div>
    <div class="box-imgs" ref="boxImgs2">
      <img v-for="big in bigImageList1" :src="realSrc(big.filePath)" alt="">
    </div>
    <div class="box-imgs" ref="boxImgs3">
      <img v-for="big in bigImageList2" :src="realSrc(big.filePath)" alt="">
    </div>
    <div class="box-imgs" ref="boxImgs4">
      <img v-for="big in bigImageList3" :src="realSrc(big.filePath)" alt="">
    </div>
  </div>
</template>
<script>
import {listItem} from "@/api/gaogao/item"
import {isExternal} from "@/utils/validate";
export default {
  name: "Index",
  data() {
    return {
      bigImageList:[],
      bigImageList1:[],
      bigImageList2:[],
      bigImageList3:[],
      radius: 700,
      boxImgs1: null,
      boxImgs2: null,
      boxImgs3: null,
      boxImgs4: null,
      imgs1: null,
      imgs2: null,
      imgs3: null,
      imgs4: null,
      dragBox: null,
      startX: 0,
      startY: 0,
      endX: 0,
      endY: 0,
      tX: 0,
      tY: 0,
      desX: 0,
      desY: 0,
      imagesLoaded: 0,
      pageSize: null,
    };
  },
  created(){

  },
  mounted(){
    let width = document.getElementById('box').offsetWidth
    this.radius = width/2
    this.pageSize = Math.ceil(2*4*width/120)
    // this.getList()
  },
  methods: {
    realSrc(src) {
      if (!src) {
        return;
      }
      let real_src = src.split(",")[0];
      if (isExternal(real_src)) {
        return real_src;
      }
      return process.env.VUE_APP_BASE_API + real_src;
    },
    getList(){

      // 照片墙圆弧半径
      let params = {
        "pageNum": 1,
        "pageSize": this.pageSize,
        "type": "1"
      }
      listItem(params).then(res=>{
        this.bigImageList=res.rows

        this.bigImageList = res.rows.slice(0, Math.round(res.rows.length / 4));
        this.bigImageList1 = res.rows.slice(Math.round(res.rows.length / 4), Math.round(res.rows.length / 4) * 2);
        this.bigImageList2 = res.rows.slice(Math.round(res.rows.length / 4) * 2, Math.round(res.rows.length / 4) * 3);
        this.bigImageList3 = res.rows.slice(Math.round(res.rows.length / 4) * 3);
        this.radius = this.bigImageList.length * 20
      })
    },
    handleImageLoad(){
      this.imagesLoaded++;
      if (this.imagesLoaded === this.bigImageList.length) {
        this.$nextTick(() => {
          this.allImagesLoaded();
        });
      }
    },
    allImagesLoaded(){
      // 每排容器
      this.boxImgs1 = this.$refs.boxImgs1;
      this.boxImgs2 = this.$refs.boxImgs2;
      this.boxImgs3 = this.$refs.boxImgs3;
      this.boxImgs4 = this.$refs.boxImgs4;
      // 每排容器中的所有图片
      this.imgs1 = Array.from(this.boxImgs1.getElementsByTagName('img'))
      this.imgs2 = Array.from(this.boxImgs2.getElementsByTagName('img'))
      this.imgs3 = Array.from(this.boxImgs3.getElementsByTagName('img'))
      this.imgs4 = Array.from(this.boxImgs4.getElementsByTagName('img'))
      // 初始化一遍
      this.reloadStyle()

      // 鼠标滚轮 || 触摸板上下滚动，不要使用捏合手势，浏览器内容默认会被放大
      document.onmousewheel = function (e) {
        // 获取捏合数值
        e || e.window.event
        const num = e.wheelDelta / 20 || -e.detail
        // 在原基础上调整角度数值
        this.radius += num
        // 重新布局
        this.reloadStyle()
      }

      // =================================================== 升级示例 - 鼠标拖拽（注释即失效）

      // 拖拽容器
      this.dragBox = document.getElementById('box')
    },
    setStyle(dom, i, len, delayTime) {
      dom.style.transform = `rotateY(${i * (360 / len)}deg) translateZ(${this.radius}px)`;
      // 缩放调整额外在追加一些动画
      // dom.style.transition = 'transform 1s';
      // dom.style.transitionDelay = `${delayTime || (len - i) / 4}s`
    },
    // 刷新一遍位置
    reloadStyle() {
      for (let i = 0; i < this.imgs1.length; i++) {
        this.setStyle(this.imgs1[i], i, this.imgs1.length, 0)
      }
      for (let i = 0; i < this.imgs2.length; i++) {
        this.setStyle(this.imgs2[i], i, this.imgs2.length, 0)
      }
      for (let i = 0; i < this.imgs3.length; i++) {
        this.setStyle(this.imgs3[i], i, this.imgs3.length, 0)
      }
      for (let i = 0; i < this.imgs4.length; i++) {
        this.setStyle(this.imgs4[i], i, this.imgs4.length, 0)
      }
    },
    // 修改拖拽角度
    changeDragBoxRotate() {
      // X轴旋转 0 - 180 度
      if (this.tY > 180) { this.tY = 180 }
      if (this.tY < 0) { this.tY = 0 }
      // Y 轴旋转角度不限制
      this.dragBox.style.transform = `rotateX(${-this.tY}deg) rotateY(${this.tX}deg)`
    },
    // 控制旋转状态
    playAutoRotate (isPlay) {
      // 状态
      const status = isPlay ? 'running' : 'paused'
      // 任意控制一层
      this.boxImgs2.style.animationPlayState = status
      this.boxImgs4.style.animationPlayState = status
    },
  }
};
</script>
<style scoped>
html, body {
  /* 禁止滚动 */
  overflow: hidden;
  /* 触摸设备上浏览器不会对触摸事件做任何处理，所有触摸事件都会被忽略 */
  touch-action: none;
  /* 禁止选中 */
  user-select: none;
  /* 清空所有状态 */
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
  background-color: #000;
}
body {
  /* 控制好摄像头位置 */
  perspective: 600px;
  perspective-origin: center;
}
.box {
  transform-style: preserve-3d;
  height: 100%;
  /* 倾斜一点 */
  transform: rotateX(-30deg);
}
.box-imgs {
  transform-style: preserve-3d;
  /* 用于内部图片坐标全部归零，好进行布局 */
  position: relative;
  /* 为了居中 */
  margin: auto;
  /* 这里的宽高是为了给下面 img 的宽高 100% 用的，可以调整去掉，设置到图片本身上去 */
  width: 120px;
  height: 140px;
  /* 自动旋转 */
  animation: autoRotate 200s infinite linear;
}
.box-imgs:not(:first-child) {
  margin-top: 6px;
}
.box-imgs img {
  transform-style: preserve-3d;
  /* 坐标归零 */
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
}
.box-imgs:last-child img {
  /* 倒影，如果有多层照片叠加，可以考虑只设置最底下一排有倒影 */
  -webkit-box-reflect: below 10px linear-gradient(transparent, transparent, #0005);
}
@keyframes autoRotate {
  from {
    transform: rotateY(0deg);
  }
  to {
    transform: rotateY(360deg);
  }
}
</style>
