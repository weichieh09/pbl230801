import axios from 'axios';
import LoginService from '@/account/login.service';

const apiBaseUrl = '/api/wcc101';

export default {
  data() {
    return {
      loginService: new LoginService(),
      form: {
        team: 3,
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
      },
      isNoData: false,
    };
  },
  created() {
    this.getNowDate();
    this.dateChange();
    this.getTeamList();
  },
  methods: {
    getIcon(index: number): boolean {
      if (this.page.currentPage === 1 && index <= 2) {
        return true;
      }
      return false;
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
            sort: 'tot_wins,mtch_end_time,desc',
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
    getISOString(input: any): string {
      return input + 'T00:00:00.000Z';
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
        .get('/api/wcc101/teams')
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
      this.dateChange();
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
      this.getRtsList();
    },
  },
};
