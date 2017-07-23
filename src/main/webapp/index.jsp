<%@ page contentType="text/html;charset=UTF-8" language="java" %><!DOCTYPE html>
<html lang="en" ng-app="sspInterviewApp">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Оценки удовлетворенности персонала</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/common.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

    <![endif]-->

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="js/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
    <!-- Angular -->
    <script src="js/angular.min.js"></script>
    <!-- Common -->
    <script src="js/common.js"></script>
</head>
<body ng-controller="interviewCtrl">
<div class="customHeader"></div>
<div class="jumbotron">
    <h1>Оценки удовлетворенности персонала</h1>
    <b><p>
        Руководство компании и служба персонала проводят анкетирование сотрудников с целью
        оптимизации
        кадровой политики компании. Ваше мнение будет обязательно учтено при разработке мер
        повышения
        эффективности системы управления персоналом. Анкета является анонимной. При ответах на
        вопросы
        Вам необходимо отметить ответ, соответствующий Вашему мнению.
    </p></b>
</div>
<form class="form-horizontal" ng-cloak>
    <div class="section" id="firstPart">
        <!-- FIRST SECTION -->
        <div class="row top20" id="team">
            <div class="form-group">
                <select class="form-control" name="priority{{i}}" required>
                    <option value="" disabled selected>Выберите вашу команду:</option>
                    <option ng-repeat="team in teams" value="{{$index+1}}">
                        {{team.label}}
                    </option>
                </select>
            </div>
        </div>
        <div class="row top20" id="priorities">
            <h2>1. Выстройте по степени значимости для Вас факторы, которые представлены ниже, первый - самый
                значимый для Вас, далее по степени убывания, последний – самый незначительный:</h2>
            <div class="form-group" ng-repeat="i in [0,1,2,3,4,5,6,7,8]">
                <select class="form-control" name="priority{{i}}" id="priority{{i}}" ng-model="selected"
                        ng-change="changeOptions(selected,{{i}})" required>
                    <option value="" disabled selected>Фактор с приоритетом {{i+1}}</option>
                    <option ng-repeat="prio in priorities" value="{{$index+1}}" ng-hide="isHidden(i,prio)">
                        {{prio.label}}
                    </option>
                </select>
            </div>
            <select ng-model="selected" id="last" ng-hide="true">
                <option ng-repeat="prio in priorities" value="{{$index+1}}" ng-hide="isHidden(i,prio)">{{$index+1}}</option>
            </select>
            <div class="form-group" ng-hide="isOthers()">
                <input type="text" class="form-control col-xs-12" name="priority_other" id="otherPriorities"
                       placeholder="Инные (укажите):" required>
            </div>
        </div>
        <div class="row top50" id="fivepoint">
            <h2>2. Оцените по пятибальной шкале следующие вопросы:</h2>
            <div>
                <p>0 – если данные отсутствуют</p>
                <p>1 – очень плохо/полностью не соответствует Вашим представлениям</p>
                <p>2 – плохо/ не соответствует Вашим представлениям</p>
                <p>3 – удовлетворительно/ частично не соответствует Вашим представлениям</p>
                <p>4 – хорошо/ практически полностью соответствует Вашим представлениям</p>
                <p>5 – отлично/ полностью соответствует Вашим представлениям</p>
            </div>
            <div class=" top20" ng-repeat="fp in fivepoint">
                <h4>{{fp.value}}</h4>
                <div class="btn-group" data-toggle="buttons" >
                    <label class="btn btn-default" ng-repeat="i in [0,1,2,3,4,5]" >
                        <input type="radio" name="fivepoint{{fp.id}}" value="{{i}}" required onchange="showIncomes($(this))"> {{i}}
                    </label>
                </div>
            </div>
            <div class="top20 form-group" id="incomes" style="display: none">
                <h4>Как много времени вы тратите на него? Указать в часах в неделю</h4>
                <input type="text" class="form-control col-xs-12" name="incomes"  required onkeypress='return event.charCode >= 48 && event.charCode <= 57'>
            </div>
        </div>
        <div class="row top50" id="yesno">
            <h2>3. Ответьте да/нет на следующие вопросы:</h2>
            <div class="top20" ng-repeat="yn in yesno">
                <h4>{{yn.value}}</h4>
                <div class="btn-group" data-toggle="buttons">
                    <label class="btn btn-default">
                        <input type="radio" name="yesno{{yn.id}}" value="1" required> Да
                    </label>
                    <label class="btn btn-default">
                        <input type="radio" name="yesno{{yn.id}}" value="0" required> Нет
                    </label>
                </div>
            </div>
        </div>
        <div class="row top50" id="question">
            <h2>4. Отметьте, пожалуйста, то утверждение, которое соответствует Вашей позиции:</h2>
            <div class="top20" style="padding-left: 0; padding-right: 0">
                <div class="btn-group-vertical" style="width: 100%;" data-toggle="buttons">
                    <label class="btn btn-default top10" ng-repeat="qu in question">
                        <input type="radio" name="question" value="{{$index}}" required>{{qu}}
                    </label>
                </div>
            </div>
        </div>
        <div class="row top50" id="additional">
            <h2>5. Если у Вас есть желание высказаться, напишите, пожалуйста, Ваши пожелания по улучшению
                бизнеса нашей компании (Вы можете пропустить ответ на этот вопрос)</h2>
            <div class="form-group">
                <textarea name="additional" rows="5" class="form-control top10"></textarea>
            </div>
            <div class="form-group">
                <button type="button" class="btn btn-default btn-lg col-xs-4 top10" ng-click="nextButton()">Далее
                </button>
            </div>
        </div>
    </div>

    <!-- SECOND SECTION -->
    <div class="section" id="secondPart">
        <div class="row">
            <h1>Оценка уровня лояльности сотрудников</h1>
        </div>
        <div class="row top20" id="loyality">
            <h2>Вам предложено несколько вопросов, выражающих разнообразные чувства, которые Вы может испытывать
                к
                нашей компании. Определите свои собственные чувства. Для этого выберите приведенный
                вариант:</h2>
            <div>
                <p>1 – абсолютно не согласен;</p>
                <p>2 – в чем-то не согласен;</p>
                <p>3 – затрудняюсь ответить;</p>
                <p>4 – согласен до некоторой степени;</p>
                <p>5 – полностью согласен.</p>
            </div>
            <div class="top20" ng-repeat="loy in loyality">
                <h4>{{loy.value}}</h4>
                <div class="btn-group" data-toggle="buttons">
                    <label class="btn btn-default" ng-repeat="i in [1,2,3,4,5]">
                        <input type="radio" name="loyality{{loy.id}}" value="{{i}}" required> {{i}}
                    </label>
                </div>
            </div>
        </div>
        <div class="row top20">
            <h2>Спасибо за Ваши ответы!</h2>
            <div class="form-group">
                <button type="button" class="btn btn-default btn-lg col-xs-4 top10" ng-click="previousButton()"
                        style="margin-right: 10px">Назад
                </button>
                <button type="button" class="btn btn-primary btn-lg col-xs-4 top10" ng-click="finishButton()">
                    Отправить
                </button>
            </div>
        </div>
    </div>
</form>

<!-- THIRD SELECTION -->
<div class="section" id="thirdPart">
    <div class="row top20">
        <div class="alert alert-success" style="margin-left: 0">
            <h3>Спасибо, ответ записан.</h3>
        </div>
    </div>
</div>
</body>
</html>