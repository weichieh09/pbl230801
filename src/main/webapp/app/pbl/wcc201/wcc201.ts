import axios from 'axios';

const apiBaseUrl = '/api/wcc201';

export default {
  data() {
    return {
      form: {
        team: null,
        teams: [],
        date: '',
        space: null,
        spaces: [{ text: '請選擇', value: null }],
        event: null,
        events: [{ text: '請選擇', value: null }],
      },
      resultForm: {
        wPlyr1: null,
        wPlyr1Nm: '選手1',
        wPlyr2: null,
        wPlyr2Nm: '選手2',
        wScr: null,
        lPlyr1: null,
        lPlyr1Nm: '選手1',
        lPlyr2: null,
        lPlyr2Nm: '選手2',
        lScr: null,
      },
      type: null,
      plyrs: [],
      page: {
        previousPage: 1,
        currentPage: 1,
        objTotal: 0,
        perPage: 5,
      },
    };
  },
  created() {
    this.getNowDate();
    this.dateChange();
    this.getTeamList();
  },
  methods: {
    saveResultForm(): void {
      axios
        .post(`${apiBaseUrl}/match-zs`, {
          eId: this.form.event,
          tId: this.form.team,
          wScr: this.resultForm.wScr,
          wPlyr1: this.resultForm.wPlyr1,
          wPlyr2: this.resultForm.wPlyr2,
          lScr: this.resultForm.lScr,
          lPlyr1: this.resultForm.lPlyr1,
          lPlyr2: this.resultForm.lPlyr2,
        })
        .then(response => {
          if (response.data.status === '0') {
            this.$bvToast.toast('儲存成功', {
              toaster: 'b-toaster-top-center',
              title: '成功',
              variant: 'success',
              solid: true,
            });
            this.resultForm.wScr = null;
            this.resultForm.wPlyr1 = null;
            this.resultForm.wPlyr1Nm = '選手1';
            this.resultForm.wPlyr2 = null;
            this.resultForm.wPlyr2Nm = '選手2';
            this.resultForm.lScr = null;
            this.resultForm.lPlyr1 = null;
            this.resultForm.lPlyr1Nm = '選手1';
            this.resultForm.lPlyr2 = null;
            this.resultForm.lPlyr2Nm = '選手2';
          } else {
            this.$bvToast.toast('資料不正確 或 該球隊未參加此賽事', {
              toaster: 'b-toaster-top-center',
              title: '失敗',
              variant: 'danger',
              solid: true,
            });
          }
        })
        .catch(error => {
          console.log(error);
          this.$bvToast.toast('儲存失敗', {
            toaster: 'b-toaster-top-center',
            title: '失敗',
            variant: 'danger',
            solid: true,
          });
        });
    },
    checkPoint(): void {
      this.$refs['checkPoint-modal'].show();
    },
    pageLoad(page: any): void {
      if (page !== this.page.previousPage) {
        this.page.previousPage = page;
        this.getPlyrList();
      }
    },
    getPlyrList(): void {
      axios
        .get(`${apiBaseUrl}/players`, {
          params: {
            'tId.equals': this.form.team,
            sort: 'plyrLvl,desc',
            page: this.page.currentPage - 1,
            size: this.page.perPage,
          },
        })
        .then(response => {
          if (response.data.length > 0) {
            this.plyrs = response.data;
            this.page.objTotal = Number(response.headers['x-total-count']);
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    getSpaceList(): void {
      axios
        .get(`${apiBaseUrl}/venues`, {
          params: {
            'evntDt.equals': this.getISOString(this.form.date),
          },
        })
        .then(response => {
          if (response.data.length > 0) {
            response.data.forEach((element: any) => {
              this.form.spaces.push({ text: element.name, value: element.name });
            });
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    getTeamList(): void {
      this.form.teams.push({ text: '請選擇', value: null });
      axios
        .get(`${apiBaseUrl}/teams`)
        .then(response => {
          response.data.forEach((element: any) => {
            this.form.teams.push({ text: element.name, value: element.id });
          });
        })
        .catch(error => {
          console.log(error);
        });
    },
    getEventList(): void {
      axios
        .get(`${apiBaseUrl}/events`, {
          params: {
            'evntDt.equals': this.getISOString(this.form.date),
            'venue.equals': this.form.space,
          },
        })
        .then(response => {
          if (response.data.length > 0) {
            response.data.forEach((element: any) => {
              this.form.events.push({ text: element.name, value: element.id });
            });
          }
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

      this.form.date = `${year}-${month}-${day}`;
    },
    getISOString(str: any): string {
      const date = new Date(str);
      date.setTime(date.getTime() - 8 * 60 * 60 * 1000);
      return date.toISOString();
    },
    showModal(type: String): void {
      this.$refs['selectPlyr-modal'].show();
      this.type = type;
    },
    hideModal(item): void {
      this.$refs['selectPlyr-modal'].hide();
      if (item === null) return;
      switch (this.type) {
        case 'wPlyr1':
          this.resultForm.wPlyr1 = item.id;
          this.resultForm.wPlyr1Nm = item.plyrNm;
          break;
        case 'wPlyr2':
          this.resultForm.wPlyr2 = item.id;
          this.resultForm.wPlyr2Nm = item.plyrNm;
          break;
        case 'lPlyr1':
          this.resultForm.lPlyr1 = item.id;
          this.resultForm.lPlyr1Nm = item.plyrNm;
          break;
        case 'lPlyr2':
          this.resultForm.lPlyr2 = item.id;
          this.resultForm.lPlyr2Nm = item.plyrNm;
          break;
        default:
          break;
      }
    },
    teamChange(): void {
      this.plyrs = [];
      this.getPlyrList();
    },
    dateChange(): void {
      this.form.spaces = [];
      this.form.spaces.push({ text: '請選擇', value: null });
      this.form.space = null;
      this.form.events = [];
      this.form.events.push({ text: '請選擇', value: null });
      this.form.event = null;
      this.getSpaceList();
    },
    spaceChange(): void {
      this.form.events = [];
      this.form.events.push({ text: '請選擇', value: null });
      this.form.event = null;
      if (this.form.space === null) return;
      this.getEventList();
    },
    eventChange(): void {
      if (this.form.event === null) return;
    },
  },
};
