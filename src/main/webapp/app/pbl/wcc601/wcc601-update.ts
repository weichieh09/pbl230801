import axios from 'axios';

const apiBaseUrl = '/api/wcc601';

export default {
  data() {
    return {
      form: {
        eId: this.$route.params.eId,
        evntNm: '',
        evntDt: '',
        venue: '',
        eventBegTime: '',
        eventEndTime: '',
        tId: null,
      },
      teams: [{ text: '請選擇', value: null }],
    };
  },
  created() {
    this.getTeamList();
    if (this.form.eId != 0) this.getEventZ();
    else this.getNowDate();
  },
  methods: {
    getTeamList(): void {
      this.teams = [];
      this.teams.push({ text: '請選擇', value: null });
      axios
        .get(`${apiBaseUrl}/teams`)
        .then(response => {
          response.data.forEach((element: any) => {
            this.teams.push({ text: element.name, value: element.id });
          });
        })
        .catch(error => {
          console.log(error);
        });
    },
    getNowDate(): void {
      const date = new Date();
      const year = date.getFullYear();
      let month = (date.getMonth() + 1).toString().padStart(2, '0');
      const day = date.getDate();

      this.form.evntDt = `${year}-${month}-${day}`;
      this.form.eventBegTime = '13:30';
      this.form.eventEndTime = '17:30';
    },
    updateEventZ(): void {
      if (!(this.form.evntNm && this.form.evntDt && this.form.venue && this.form.eventBegTime && this.form.eventEndTime)) {
        this.$bvToast.toast('請輸入賽事名稱', {
          toaster: 'b-toaster-top-center',
          title: '失敗',
          variant: 'danger',
          solid: true,
        });
        return;
      }
      axios
        .put(`${apiBaseUrl}/event-zs/${this.form.eId}`, this.form)
        .then(response => {
          if (response.data.status === '0') {
            this.$bvToast.toast('修改賽事成功', {
              toaster: 'b-toaster-top-center',
              title: '修改成功',
              variant: 'success',
              solid: true,
            });
          } else {
            this.$bvToast.toast('資料不正確', {
              toaster: 'b-toaster-top-center',
              title: '修改失敗',
              variant: 'danger',
              solid: true,
            });
          }
        })
        .catch(error => {
          console.log(error);
          this.$bvToast.toast('修改賽事失敗', {
            toaster: 'b-toaster-top-center',
            title: '修改失敗',
            variant: 'danger',
            solid: true,
          });
        });
    },
    getSimpleTime(str: string): string {
      const date = new Date(str);
      const year = date.getFullYear();
      const month = (date.getMonth() + 1).toString().padStart(2, '0');
      const day = date.getDate().toString().padStart(2, '0');
      const hour = date.getHours().toString().padStart(2, '0');
      const minute = date.getMinutes().toString().padStart(2, '0');
      return `${year}-${month}-${day} ${hour}:${minute}`;
    },
    getEventZ(): void {
      axios
        .get(`${apiBaseUrl}/event-zs/${this.form.eId}`)
        .then(response => {
          if (response.data != null) {
            this.form.evntNm = response.data.evntNm;
            this.form.venue = response.data.venue;
            this.form.evntDt = this.getSimpleTime(response.data.evntDt).substring(0, 10);
            this.form.eventBegTime = this.getSimpleTime(response.data.eventBegTime).substring(11, 16);
            this.form.eventEndTime = this.getSimpleTime(response.data.eventEndTime).substring(11, 16);
            this.form.tId = response.data.tId;
          } else {
            this.$bvToast.toast('讀取賽事資料失敗', {
              toaster: 'b-toaster-top-center',
              title: '讀取失敗',
              variant: 'danger',
              solid: true,
            });
          }
        })
        .catch(error => {
          console.log(error);
          this.$bvToast.toast('讀取賽事資料失敗', {
            toaster: 'b-toaster-top-center',
            title: '讀取失敗',
            variant: 'danger',
            solid: true,
          });
        });
    },
    saveEventZ(): void {
      if (!(this.form.evntNm && this.form.evntDt && this.form.venue && this.form.eventBegTime && this.form.eventEndTime)) {
        this.$bvToast.toast('請輸入賽事資訊', {
          toaster: 'b-toaster-top-center',
          title: '失敗',
          variant: 'danger',
          solid: true,
        });
        return;
      }
      axios
        .post(`${apiBaseUrl}/event-zs`, this.form)
        .then(response => {
          if (response.data.status === '0') {
            this.$bvToast.toast('新增賽事成功', {
              toaster: 'b-toaster-top-center',
              title: '新增成功',
              variant: 'success',
              solid: true,
            });
            this.form.evntNm = '';
            this.form.venue = '';
            this.getNowDate();
            this.form.tId = null;
          } else {
            this.$bvToast.toast('資料不正確', {
              toaster: 'b-toaster-top-center',
              title: '新增失敗',
              variant: 'danger',
              solid: true,
            });
          }
        })
        .catch(error => {
          console.log(error);
          this.$bvToast.toast('新增賽事失敗', {
            toaster: 'b-toaster-top-center',
            title: '新增失敗',
            variant: 'danger',
            solid: true,
          });
        });
    },
  },
};
