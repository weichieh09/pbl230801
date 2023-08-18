import axios from 'axios';
import LoginService from '@/account/login.service';

const apiBaseUrl = '/api/wcc701';

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
      xlsxData: [],
      xlsxType: '',
    };
  },
  created() {
    this.getNowDate();
    this.dateChange();
    this.getTeamList();
  },
  methods: {
    getSbList(): void {
      if (this.form.event === null) return;
      if (this.form.team === null) return;
      this.xlsxData = [];
      axios
        .get(`${apiBaseUrl}/vw-wcc-701-results`, {
          params: {
            'eId.equals': this.form.event,
            'tId.equals': this.form.team,
          },
        })
        .then(response => {
          if (response.data.length > 0) {
            this.xlsxData = response.data;
            this.xlsxType = '戰績表';
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    getXlsxName(): string {
      return `${this.form.date}_${this.form.events.find(option => option.value === this.form.event).text}_${this.xlsxType}.xlsx`;
    },
    getRtsList(): void {
      if (this.form.event === null) return;
      if (this.form.team === null) return;
      this.xlsxData = [];
      axios
        .get(`${apiBaseUrl}/realTimeScore?eId.equals=${this.form.event}&tId.equals=${this.form.team}`)
        .then(response => {
          if (response.data.length > 0) {
            this.xlsxData = response.data;
            this.xlsxType = '英雄榜';
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
    teamChange(): void {
      this.xlsxData = [];
      if (this.form.event === null) return;
      if (this.form.team === null) return;
    },
    dateChange(): void {
      this.xlsxData = [];
      this.form.spaces = [];
      this.form.spaces.push({ text: '請選擇', value: null });
      this.form.space = null;
      this.form.events = [];
      this.form.events.push({ text: '請選擇', value: null });
      this.form.event = null;
      this.getSpaceList();
    },
    spaceChange(): void {
      this.xlsxData = [];
      this.form.events = [];
      this.form.events.push({ text: '請選擇', value: null });
      this.form.event = null;
      if (this.form.space === null) return;
      this.getEventList();
    },
    eventChange(): void {
      this.xlsxData = [];
      if (this.form.event === null) return;
      if (this.form.team === null) return;
    },
  },
};
