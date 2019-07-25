<template>
    <div>
      <Row :gutter="16">
        <Col :xs="2" :sm="4" :md="6" :lg="8">
          <Card>
              <div class="chart-height"  id="myChart" ref="myChart">

              </div>
          </Card>
        </Col>
        <Col :xs="20" :sm="16" :md="12" :lg="8">
          <Card>
            <div>
              <div  class="chart-height" id="myChartsp" ref="myChartsp">

              </div>
            </div>
          </Card>
        </Col>
        <Col :xs="2" :sm="4" :md="6" :lg="8">
          <Card>
            <div>
              <div class="chart-height" id="myChartsh" ref="myChartsh">

              </div>
            </div>
          </Card>
        </Col>
      </Row>

    </div>
</template>
<script>
// import echarts from '@/libs/echart'
import echarts from 'echarts'
export default {
  data () {
    return {

    }
  },
  methods: {
    draw () {
      // 实例化echarts对象
      let myChart = echarts.init(this.$refs.myChart)
      let myChartsp = echarts.init(this.$refs.myChartsp)
      let myChartsh = echarts.init(this.$refs.myChartsh)
      // var myChart = echarts.init(document.getElementById('myChart'));
      // 绘制条形图
      var dataAxis = ['点', '击', '柱', '子', '或', '者', '两', '指', '在', '触', '屏', '上', '滑', '动', '能', '够', '自', '动', '缩', '放']
      var data = [220, 182, 191, 234, 290, 330, 310, 123, 442, 321, 90, 149, 210, 122, 133, 334, 198, 123, 125, 220]
      var yMax = 500
      var dataShadow = []

      for (var i = 0; i < data.length; i++) {
        dataShadow.push(yMax)
      }
      var zoomSize = 6
      myChart.on('click', function (params) {
        console.log(dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)])
        myChart.dispatchAction({
          type: 'dataZoom',
          startValue: dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)],
          endValue: dataAxis[Math.min(params.dataIndex + zoomSize / 2, data.length - 1)]
        })
      })
      myChart.setOption({
        title: {
          text: '特性示例：渐变色 阴影 点击缩放'
        },
        xAxis: {
          data: dataAxis,
          axisLabel: {
            inside: true,
            textStyle: {
              color: '#fff'
            }
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          z: 10
        },
        yAxis: {
          axisLine: {
            show: false
          },
          axisTick: {
            show: false
          },
          axisLabel: {
            textStyle: {
              color: '#999'
            }
          }
        },
        dataZoom: [
          {
            type: 'inside'
          }
        ],
        series: [
          { // For shadow
            type: 'bar',
            itemStyle: {
              normal: { color: 'rgba(0,0,0,0.05)' }
            },
            barGap: '-100%',
            barCategoryGap: '40%',
            data: dataShadow,
            animation: false
          },
          {
            type: 'bar',
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(
                  0, 0, 0, 1,
                  [
                    { offset: 0, color: '#83bff6' },
                    { offset: 0.5, color: '#188df0' },
                    { offset: 1, color: '#188df0' }
                  ]
                )
              },
              emphasis: {
                color: new echarts.graphic.LinearGradient(
                  0, 0, 0, 1,
                  [
                    { offset: 0, color: '#2378f7' },
                    { offset: 0.7, color: '#2378f7' },
                    { offset: 1, color: '#83bff6' }
                  ]
                )
              }
            },
            data: data
          }
        ]

      }),
      myChartsp.setOption({
        title: {
          text: '统计图',
          subtext: '数据来自网络'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: { // 坐标轴指示器，坐标轴触发有效
            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        legend: {
          data: ['利润', '支出', '收入']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'value'
          }
        ],
        yAxis: [
          {
            type: 'category',
            axisTick: { show: false },
            data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
          }
        ],
        series: [
          {
            name: '利润',
            type: 'bar',
            label: {
              normal: {
                show: true,
                position: 'inside'
              }
            },
            data: [200, 170, 240, 244, 200, 220, 210]
          },
          {
            name: '收入',
            type: 'bar',
            stack: '总量',
            label: {
              normal: {
                show: true
              }
            },
            data: [320, 302, 341, 374, 390, 450, 420]
          },
          {
            name: '支出',
            type: 'bar',
            stack: '总量',
            label: {
              normal: {
                show: true,
                position: 'left'
              }
            },
            data: [-120, -132, -101, -134, -190, -230, -210]
          }
        ]
      }),
      myChartsh.setOption({
        title: {
          text: '世界人口总量',
          subtext: '数据来自网络'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          data: ['2011年', '2012年']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value',
          boundaryGap: [0, 0.01]
        },
        yAxis: {
          type: 'category',
          data: ['巴西', '印尼', '美国', '印度', '中国', '世界人口(万)']
        },
        series: [
          {
            name: '2011年',
            type: 'bar',
            data: [18203, 23489, 29034, 104970, 131744, 630230]
          },
          {
            name: '2012年',
            type: 'bar',
            data: [19325, 23438, 31000, 121594, 134141, 681807]
          }
        ]
      })
    },
    init () {
      const self = this
      setTimeout(() => {
        window.onresize = function () {
          self.chart = echarts.init(self.$refs.myChart)
          self.chart2 = echarts.init(self.$refs.myChartsp)
          self.chart3 = echarts.init(self.$refs.myChartsh)
          self.chart.resize()
          self.chart2.resize()
          self.chart3.resize()
        }
      }, 20)
    }
  },
  mounted () {
    setTimeout(() => {
      this.draw()
      this.init()
    })
  }
}
</script>

<style scoped>

  .chart-height {
    width: 100% !important;
    overflow: hidden;
    height: 300px;
  }
</style>
