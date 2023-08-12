import axios from 'axios';

const apiBaseUrl = '/api/wcc101';

export default {
  data() {
    return {
      form: {
        team: 3,
        teams: [],
        date: '',
        space: null,
        spaces: [{ text: '請選擇', value: null }],
        event: null,
        events: [{ text: '請選擇', value: null }],
      },
      resultForm: {
        wPlyr1: '選手1',
        wPlyr2: '選手2',
        wScr: null,
        lPlyr1: '選手1',
        lPlyr2: '選手2',
        lScr: null,
      },
      searchName: '',
      type: null,
      teams: [
        { text: '請選擇', value: null },
        { text: '運動家羽球隊', value: '01' },
        { text: '夢想號不用燃料', value: '02' },
        { text: '大聯盟隊', value: '03' },
      ],
      spaces: [
        { text: '請選擇', value: null },
        { text: '台藝大', value: '01' },
        { text: '羽協', value: '02' },
        { text: '鑫高手', value: '03' },
      ],
      items: [
        { id: '1', class: '12', plyrNm: 'Mark' },
        { id: '2', class: '14', plyrNm: 'Jacob' },
        { id: '3', class: '15', plyrNm: 'Larry' },
        { id: '4', class: '14', plyrNm: 'Sam' },
        { id: '5', class: '14', plyrNm: 'Jacky' },
      ],
      perPage: 3,
      currentPage: 1,
      rows: 10,
    };
  },
  created() {
    this.getNowDate();
    this.getTeamList();
    this.getSpaceList();
  },
  methods: {
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

      // this.form.date = `${year}-${month}-${day}`;
      this.form.date = '2023-08-01';
    },
    getISOString(input: any): string {
      return input + 'T00:00:00.000Z';
    },
    showModal(type: String): void {
      this.$refs['my-modal'].show();
      console.log('showModal ---> ' + type);
      this.type = type;
    },
    hideModal(item): void {
      this.$refs['my-modal'].hide();
      console.log('hideModal ---> ' + item.id);
      switch (this.type) {
        case 'wPlyr1':
          this.resultForm.wPlyr1 = item.plyrNm;
          break;
        case 'wPlyr2':
          this.resultForm.wPlyr2 = item.plyrNm;
          break;
        case 'lPlyr1':
          this.resultForm.lPlyr1 = item.plyrNm;
          break;
        case 'lPlyr2':
          this.resultForm.lPlyr2 = item.plyrNm;
          break;
        default:
          break;
      }
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
    },
  },
};
