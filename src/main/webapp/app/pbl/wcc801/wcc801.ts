import axios from 'axios';
import LoginService from '@/account/login.service';

const apiBaseUrl = '/api/wcc801';

export default {
  data() {
    return {
      loginService: new LoginService(),
      form: {
        team: null,
        teams: [],
        date: '',
        space: null,
        spaces: [{ text: '請選擇', value: null }],
        event: null,
        events: [{ text: '請選擇', value: null }],
      },
      rtss: [],
      page: {
        previousPage: 1,
        currentPage: 1,
        objTotal: 0,
        perPage: 5,
        sort: 'tot_wins,mtch_end_time,desc',
      },
      isNoData: false,
      sort: {
        reverse: false,
        type: '',
      },
    };
  },
  created() {
    this.getNowDate();
    this.dateChange();
    this.getTeamList();
  },
  methods: {
    changeOrder(type: string): void {
      if (type === 'plyr_nm') {
        this.page.sort = 'tot_wins,mtch_end_time,desc';
        this.sort.reverse = false;
        this.sort.type = '';
      } else {
        this.sort.type = type;
        this.page.sort = type + ',' + (this.sort.reverse ? 'desc' : 'asc');
        this.sort.reverse = !this.sort.reverse;
      }
      this.getRtsList();
    },
    checkFlag(rts: any): void {
      axios
        .post(`${apiBaseUrl}/checkFlag`, {
          eId: this.form.event,
          pId: rts.pId,
          chkFg: rts.chkFg === 'Y' ? 'N' : 'Y',
        })
        .then(response => {
          if (response.data.status === '0') rts.chkFg = rts.chkFg === 'Y' ? 'N' : 'Y';
        })
        .catch(error => {
          console.log(error);
        });
    },
    pageLoad(page: any): void {
      if (page !== this.page.previousPage) {
        this.page.previousPage = page;
        this.getRtsList();
      }
    },
    getRtsList(): void {
      axios
        .get(`${apiBaseUrl}/realTimeScore`, {
          params: {
            'eId.equals': this.form.event,
            'tId.equals': this.form.team,
            sort: this.page.sort,
            page: this.page.currentPage - 1,
            size: this.page.perPage,
          },
        })
        .then(response => {
          if (response.data.length > 0) {
            this.isNoData = false;
            this.rtss = response.data;
            this.page.objTotal = Number(response.headers['x-total-count']);
          } else {
            this.isNoData = true;
          }
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
    getISOString(str: any): string {
      const date = new Date(str);
      date.setTime(date.getTime() - 8 * 60 * 60 * 1000);
      return date.toISOString();
    },
    getNowDate(): void {
      const date = new Date();
      const year = date.getFullYear();
      let month = (date.getMonth() + 1).toString().padStart(2, '0');
      const day = date.getDate();

      this.form.date = `${year}-${month}-${day}`;
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
    openLogin(): void {
      this.loginService.openLogin(this.$root);
    },
    teamChange(): void {
      this.rtss = [];
      if (this.form.event === null) return;
      if (this.form.team === null) return;
      this.getRtsList();
    },
    dateChange(): void {
      this.form.spaces = [];
      this.form.spaces.push({ text: '請選擇', value: null });
      this.form.space = null;
      this.form.events = [];
      this.form.events.push({ text: '請選擇', value: null });
      this.form.event = null;
      this.rtss = [];
      this.getSpaceList();
    },
    spaceChange(): void {
      this.form.events = [];
      this.form.events.push({ text: '請選擇', value: null });
      this.form.event = null;
      this.rtss = [];
      if (this.form.space === null) return;
      this.getEventList();
    },
    eventChange(): void {
      this.rtss = [];
      if (this.form.event === null) return;
      if (this.form.team === null) return;
      this.getRtsList();
    },
  },
};
