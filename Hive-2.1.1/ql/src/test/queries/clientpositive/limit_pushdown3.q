set hive.mapred.mode=nonstrict;
set hive.explain.user=false;
set hive.limit.pushdown.memory.usage=0.3f;
set hive.optimize.reducededuplication.min.reducer=4;

explain
select key,value from src order by key limit 20;
select key,value from src order by key limit 20;

explain
select key,value from src order by key desc limit 20;
select key,value from src order by key desc limit 20;

explain
select value, sum(key + 1) as sum from src group by value order by value limit 20;
select value, sum(key + 1) as sum from src group by value order by value limit 20;

-- deduped RS
explain
select value,avg(key + 1) from src group by value order by value limit 20;
select value,avg(key + 1) from src group by value order by value limit 20;

-- distincts
explain
select distinct(cdouble) as dis from alltypesorc order by dis limit 20;
select distinct(cdouble) as dis from alltypesorc order by dis limit 20;

explain
select ctinyint, count(distinct(cdouble)) from alltypesorc group by ctinyint order by ctinyint limit 20;
select ctinyint, count(distinct(cdouble)) from alltypesorc group by ctinyint order by ctinyint limit 20;

explain 
select ctinyint, count(cdouble) from (select ctinyint, cdouble from alltypesorc group by ctinyint, cdouble) t1 group by ctinyint order by ctinyint limit 20;
select ctinyint, count(cdouble) from (select ctinyint, cdouble from alltypesorc group by ctinyint, cdouble) t1 group by ctinyint order by ctinyint limit 20;

-- multi distinct
explain
select ctinyint, count(distinct(cstring1)), count(distinct(cstring2)) from alltypesorc group by ctinyint order by ctinyint limit 20;
select ctinyint, count(distinct(cstring1)), count(distinct(cstring2)) from alltypesorc group by ctinyint order by ctinyint limit 20;

-- limit zero
explain
select key,value from src order by key limit 0;
select key,value from src order by key limit 0;

-- 2MR (applied to last RS)
explain
select value, sum(key) as sum from src group by value order by sum limit 20;
select value, sum(key) as sum from src group by value order by sum limit 20;

set hive.map.aggr=false;
-- map aggregation disabled
explain
select value, sum(key) as sum from src group by value order by value limit 20;
select value, sum(key) as sum from src group by value order by value limit 20;

set hive.limit.pushdown.memory.usage=0.00002f;

-- flush for order-by
explain
select key,value,value,value,value,value,value,value,value from src order by key limit 100;
select key,value,value,value,value,value,value,value,value from src order by key limit 100;

-- flush for group-by
explain
select sum(key) as sum from src group by concat(key,value,value,value,value,value,value,value,value,value) order by sum limit 100;
select sum(key) as sum from src group by concat(key,value,value,value,value,value,value,value,value,value) order by sum limit 100;
