import axios from 'axios';

const apiBaseUrl = '/api/wcc501';

export default {
  data() {
    return {
      form: {
        tId: this.$route.params.tId,
        pId: this.$route.params.pId,
        plyrLvl: '',
        plyrNm: '',
      },
    };
  },
  created() {
    if (this.form.pId != 0) this.getPlyr();
  },
  methods: {
    updatePlyr(): void {
      if (!(this.form.plyrLvl && this.form.plyrNm)) {
        this.$bvToast.toast('請輸入球員資料', {
          toaster: 'b-toaster-top-center',
          title: '失敗',
          variant: 'danger',
          solid: true,
        });
        return;
      }
      axios
        .put(`${apiBaseUrl}/players/${this.form.tId}/${this.form.pId}`, {
          id: this.form.pId,
          plyrLvl: this.form.plyrLvl,
          plyrNm: this.form.plyrNm,
        })
        .then(response => {
          if (response.data.status === '0') {
            this.$bvToast.toast('修改球員成功', {
              toaster: 'b-toaster-top-center',
              title: '修改成功',
              variant: 'success',
              solid: true,
            });
          } else {
            this.$bvToast.toast('資料不正確 或 球員名稱已存在', {
              toaster: 'b-toaster-top-center',
              title: '修改失敗',
              variant: 'danger',
              solid: true,
            });
          }
        })
        .catch(error => {
          console.log(error);
          this.$bvToast.toast('修改球員失敗', {
            toaster: 'b-toaster-top-center',
            title: '修改失敗',
            variant: 'danger',
            solid: true,
          });
        });
    },
    getPlyr(): void {
      axios
        .get(`${apiBaseUrl}/players/${this.form.pId}`)
        .then(response => {
          if (response.data != null) {
            this.form.pId = response.data.id;
            this.form.plyrLvl = response.data.plyrLvl;
            this.form.plyrNm = response.data.plyrNm;
          } else {
            this.$bvToast.toast('讀取球隊資料失敗', {
              toaster: 'b-toaster-top-center',
              title: '讀取失敗',
              variant: 'danger',
              solid: true,
            });
          }
        })
        .catch(error => {
          console.log(error);
          this.$bvToast.toast('讀取球隊資料失敗', {
            toaster: 'b-toaster-top-center',
            title: '讀取失敗',
            variant: 'danger',
            solid: true,
          });
        });
    },
    savePlyr(): void {
      if (!(this.form.plyrLvl && this.form.plyrNm)) {
        this.$bvToast.toast('請輸入球員資料', {
          toaster: 'b-toaster-top-center',
          title: '失敗',
          variant: 'danger',
          solid: true,
        });
        return;
      }
      axios
        .post(`${apiBaseUrl}/players`, this.form)
        .then(response => {
          if (response.data.status === '0') {
            this.$bvToast.toast('新增球員成功', {
              toaster: 'b-toaster-top-center',
              title: '新增成功',
              variant: 'success',
              solid: true,
            });
            this.form.tName = '';
          } else {
            this.$bvToast.toast('資料不正確 或 球員名稱已存在', {
              toaster: 'b-toaster-top-center',
              title: '新增失敗',
              variant: 'danger',
              solid: true,
            });
          }
        })
        .catch(error => {
          console.log(error);
          this.$bvToast.toast('新增球員失敗', {
            toaster: 'b-toaster-top-center',
            title: '新增失敗',
            variant: 'danger',
            solid: true,
          });
        });
    },
  },
};
