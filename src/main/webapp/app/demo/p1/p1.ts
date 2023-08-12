import axios from 'axios';
import LoginService from '@/account/login.service';

const apiBaseUrl = '/api/wcc101';

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
      },
    };
  },
  created() {
    this.getNowDate();
    this.getTeamList();
  },
  methods: {
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
            sort: 'mtch_end_time,desc',
            page: this.page.currentPage - 1,
            size: this.page.perPage,
          },
        })
        .then(response => {
          console.log(response);
          if (response.data.length > 0) {
            this.rtss = response.data;
            this.page.objTotal = Number(response.headers['x-total-count']);
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
          console.log(response);
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
          console.log(response);
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
      console.log('teamChange ---> ' + this.form.team);
      this.dateChange();
    },
    dateChange(): void {
      console.log('dateChange ---> ' + this.form.date);
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
      console.log('spaceChange ---> ' + this.form.space);
      this.form.events = [];
      this.form.events.push({ text: '請選擇', value: null });
      this.form.event = null;
      this.rtss = [];
      this.getEventList();
    },
    eventChange(): void {
      console.log('eventChange ---> ' + this.form.event);
      this.rtss = [];
      this.getRtsList();
    },
  },
};
