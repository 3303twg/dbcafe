if ( $(".datepicker_wrap").length ) {
	exampleApp = angular.module('example', ['ngMessages', 'daterangepicker']);
	exampleApp.controller('dateCtrl', function ($scope) {
		$scope.past = {
			startDate: moment().subtract(1, "days"),
			endDate: moment()
		};
		$scope.future = {
			startDate: moment(),
			endDate: moment().add(1, 'days')
		};
		$scope.single = moment();

		$scope.future = {
			startDate: moment(),
			endDate: moment().add(1, 'days')
		};

		$scope.today = {
			startDate: moment(),
			endDate: moment()
		};
		$scope.yesterday = {
			startDate: moment().subtract(1, 'days'),
			endDate: moment().subtract(1, 'days')
		};
		$scope.day7 = {
			startDate: moment().subtract(6, 'days'),
			endDate: moment()
		};
		$scope.day30 = {
			startDate: moment().subtract(29, 'days'),
			endDate: moment()
		};
		$scope.future_day30 = {
			startDate: moment(),
			endDate: moment().add(29, 'days')
		};
		$scope.past_day7 = {
			startDate: moment().subtract(7, 'days'),
			endDate: moment().subtract(1, 'days')
		};
		$scope.moment = {
			startDate: moment().startOf('month'),
			endDate: moment()
		};

		$scope.select_opts = {
			maxDate: moment()
		};

		$scope.past_opts = {
			maxDate: moment(),
			ranges: {
				'어제': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
				'이번달': [moment().startOf('month'), moment()],
				'이번주': [moment().startOf('week'), moment()],
				'지난30일': [moment().subtract(30, 'days'), moment().subtract(1, 'days')],
				'지난7일': [moment().subtract(7, 'days'), moment().subtract(1, 'days')],
				'지난달': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')],
				'지난주': [moment().subtract(1, 'weeks').startOf('week'), moment().subtract(1, 'weeks').endOf('week')],
				'지난90일': [moment().subtract(90, 'days'), moment().subtract(1, 'days')],
			},
		};
		$scope.future_opts = {
			minDate: moment(),
		};

		$scope.single_opts = {
			singleDatePicker: true,
		};

		$scope.single_past_opts = {
			maxDate: moment(),
			singleDatePicker: true,
		};
		$scope.single_future_opts = {
			minDate: moment(),
			singleDatePicker: true,
		};
	});

	angular.bootstrap(document, ['example']);
};