import axios from 'axios';

const apiBaseUrl = '/api/wcc301';

export default {
  data() {
    return {
      teams: [],
      page: {
        previousPage: 1,
        currentPage: 1,
        objTotal: 0,
        perPage: 5,
      },
    };
  },
  created() {
    this.getTeamList();
  },
  methods: {
    pageLoad(page: any): void {
      if (page !== this.page.previousPage) {
        this.page.previousPage = page;
        this.getTeamList();
      }
    },
    getTeamList(): void {
      axios
        .get(`${apiBaseUrl}/teams`, {
          params: {
            sort: 'id,desc',
            page: this.page.currentPage - 1,
            size: this.page.perPage,
          },
        })
        .then(response => {
          if (response.data.length > 0) {
            this.teams = response.data;
            this.page.objTotal = Number(response.headers['x-total-count']);
          }
        });
    },
  },
};
