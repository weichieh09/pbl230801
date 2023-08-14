import axios from 'axios';

const apiBaseUrl = '/api/wcc401';

export default {
  data() {
    return {
      form: {
        tId: this.$route.params.tId,
        tName: '',
      },
    };
  },
  created() {
    if (this.form.tId != 0) this.getTeam();
  },
  methods: {
    updateTeam(): void {
      if (!this.form.tName) {
        this.$bvToast.toast('請輸入球隊名稱', {
          toaster: 'b-toaster-top-center',
          title: '失敗',
          variant: 'danger',
          solid: true,
        });
        return;
      }
      axios
        .put(`${apiBaseUrl}/teams/${this.form.tId}`, { id: this.form.tId, teamNm: this.form.tName })
        .then(response => {
          if (response.data.status === '0') {
            this.$bvToast.toast('修改球隊成功', {
              toaster: 'b-toaster-top-center',
              title: '修改成功',
              variant: 'success',
              solid: true,
            });
          } else {
            this.$bvToast.toast('資料不正確 或 球隊名稱已存在', {
              toaster: 'b-toaster-top-center',
              title: '修改失敗',
              variant: 'danger',
              solid: true,
            });
          }
        })
        .catch(error => {
          console.log(error);
          this.$bvToast.toast('修改球隊失敗', {
            toaster: 'b-toaster-top-center',
            title: '修改失敗',
            variant: 'danger',
            solid: true,
          });
        });
    },
    getTeam(): void {
      axios
        .get(`${apiBaseUrl}/teams/${this.form.tId}`)
        .then(response => {
          if (response.data != null) {
            this.form.tName = response.data.teamNm;
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
    saveTeam(): void {
      if (!this.form.tName) {
        this.$bvToast.toast('請輸入球隊名稱', {
          toaster: 'b-toaster-top-center',
          title: '失敗',
          variant: 'danger',
          solid: true,
        });
        return;
      }
      axios
        .post(`${apiBaseUrl}/teams`, { id: this.form.tId, teamNm: this.form.tName })
        .then(response => {
          if (response.data.status === '0') {
            this.$bvToast.toast('新增球隊成功', {
              toaster: 'b-toaster-top-center',
              title: '新增成功',
              variant: 'success',
              solid: true,
            });
            this.form.tName = '';
          } else {
            this.$bvToast.toast('資料不正確 或 球隊名稱已存在', {
              toaster: 'b-toaster-top-center',
              title: '新增失敗',
              variant: 'danger',
              solid: true,
            });
          }
        })
        .catch(error => {
          console.log(error);
          this.$bvToast.toast('新增球隊失敗', {
            toaster: 'b-toaster-top-center',
            title: '新增失敗',
            variant: 'danger',
            solid: true,
          });
        });
    },
  },
};
