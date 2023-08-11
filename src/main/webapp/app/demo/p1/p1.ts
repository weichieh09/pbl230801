import axios from 'axios';
import LoginService from '@/account/login.service';

export default {
  data() {
    return {
      loginService: new LoginService(),
      form: {
        team: null,
        date: '',
        space: null,
        event: '',
      },
      teams: [],
      spaces: [],
      items: [
        { class: '12', plyrNm: 'Mark', totWins: '5', matchEndTime: '11:40' },
        { class: '14', plyrNm: 'Jacob', totWins: '4', matchEndTime: '11:40' },
        { class: '15', plyrNm: 'Larry', totWins: '4', matchEndTime: '11:45' },
        { class: '14', plyrNm: 'Sam', totWins: '3', matchEndTime: '11:30' },
      ],
    };
  },
  created() {
    this.getNowDate();
    this.getTeamList();
    this.getSpaceList();
  },
  methods: {
    getSpaceList(): void {
      this.spaces = [];
      this.spaces.push({ text: '請選擇', value: null });
      axios
        .get('/api/event-zs?evntDt.equals=2023-08-01T08%3A00%3A00%2B08%3A00')
        .then(response => {
          console.log(response);
          response.data.forEach((element: any) => {
            this.spaces.push({ text: element.venue, value: element.id });
          });
        })
        .catch(error => {
          console.log(error);
        });
    },
    getNowDate(): void {
      const date = new Date();
      const year = date.getFullYear();
      const month = date.getMonth() + 1;
      if (month < 10) {
        month = '0' + month.toString();
      }
      const day = date.getDate();
      this.form.date = year + '-' + month + '-' + day;
    },
    getTeamList(): void {
      this.teams.push({ text: '請選擇', value: null });
      axios
        .get('/api/wcc101/teams')
        .then(response => {
          response.data.forEach((element: any) => {
            this.teams.push({ text: element.teamNm, value: element.id });
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
    },
  },
};
