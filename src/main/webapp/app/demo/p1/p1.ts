import axios from 'axios';
import LoginService from '@/account/login.service';

export default {
  data() {
    return {
      loginService: new LoginService(),
      form: {
        team: null,
        teams: [],
        date: '',
        space: null,
        spaces: [],
        event: null,
        events: [{ text: '請選擇', value: null }],
      },
      rtss: [],
    };
  },
  created() {
    this.getNowDate();
    this.getTeamList();
    this.getSpaceList();
  },
  methods: {
    getRtsList(): void {
      this.rtss = [];
      axios
        .get('/api/wcc101/realTimeScore?eId.equals=' + this.form.event + '&sort=mtch_end_time,desc&page=0&size=8')
        .then(response => {
          console.log(response);
          if (response.data.length > 0) {
            this.rtss = response.data;
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    getEventList(): void {
      this.form.events = [];
      this.form.events.push({ text: '請選擇', value: null });
      this.form.event = null;
      this.rtss = [];
      axios
        .get('/api/wcc101/events?evntDt.equals=' + this.getISOString(this.form.date) + '&venue.equals=' + this.form.space)
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
      this.form.spaces = [];
      this.form.spaces.push({ text: '請選擇', value: null });
      this.form.space = null;
      this.form.events = [];
      this.form.events.push({ text: '請選擇', value: null });
      this.form.event = null;
      this.rtss = [];
      axios
        .get('/api/wcc101/venues?evntDt.equals=' + this.getISOString(this.form.date))
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
    },
    dateChange(): void {
      console.log('dateChange ---> ' + this.form.date);
      this.getSpaceList();
    },
    spaceChange(): void {
      console.log('spaceChange ---> ' + this.form.space);
      this.getEventList();
    },
    eventChange(): void {
      console.log('eventChange ---> ' + this.form.event);
      this.getRtsList();
    },
  },
};
