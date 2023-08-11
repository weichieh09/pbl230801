export default {
  data() {
    return {
      form: {
        team: null,
        date: '2023-08-10',
        space: null,
        event: '請選擇',
      },
      resultForm: {
        wPlyr1: '選手1',
        wPlyr2: '選手2',
        wScr: 0,
        lPlyr1: '選手1',
        lPlyr2: '選手2',
        lScr: 0,
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
  methods: {
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
    },
    dateChange(): void {
      console.log('dateChange ---> ' + this.form.date);
    },
    spaceChange(): void {
      console.log('spaceChange ---> ' + this.form.space);
      // 賽事event改變
      switch (this.form.space) {
        case '01':
          this.form.event = '爭分奪勝搶水果';
          break;
        case '02':
          this.form.event = '棒打鴛鴦';
          break;
        case '03':
          this.form.event = '魔王挑戰晉級賽';
          break;
        default:
          this.form.event = '沒有資料';
          break;
      }
    },
  },
};
